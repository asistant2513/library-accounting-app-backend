package com.havrylenko.library.controller.ui;

import com.havrylenko.library.model.dto.LibrarianDTO;
import com.havrylenko.library.model.dto.PasswordDto;
import com.havrylenko.library.model.entity.Address;
import com.havrylenko.library.model.entity.Librarian;
import com.havrylenko.library.model.entity.PersonDetails;
import com.havrylenko.library.model.enums.Gender;
import com.havrylenko.library.model.security.Role;
import com.havrylenko.library.service.AddressService;
import com.havrylenko.library.service.LibrarianService;
import com.havrylenko.library.service.PersonDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/admin/librarians")
public class AdminLibrariansController {
    private final LibrarianService librarianService;
    private final PersonDetailsService personDetailsService;
    private final AddressService addressService;
    private final BCryptPasswordEncoder encoder;

    public AdminLibrariansController(LibrarianService librarianService,
                           PersonDetailsService personDetailsService,
                           AddressService addressService,
                           BCryptPasswordEncoder encoder) {
        this.librarianService = librarianService;
        this.personDetailsService = personDetailsService;
        this.addressService = addressService;
        this.encoder = encoder;
    }

    @GetMapping()
    public String getLibrariansView(Model model) {
        List<LibrarianDTO> dtos = librarianService.getAll().stream().map(l -> LibrarianDTO.fromLibrarian(l, false)).toList();
        model.addAttribute("librarians", dtos);
        return "admin/librarian/admin_librarians";
    }

    @GetMapping("/{id}")
    public String getOneLibrarianView(Model model, @PathVariable String id) {
        Librarian librarian = librarianService.getOneById(id).get();
        model.addAttribute("librarian", LibrarianDTO.fromLibrarian(librarian, false));
        return "admin/librarian/admin_single_librarian";
    }

    @GetMapping("/add")
    public String addLibrarian(Model model) {
        model.addAttribute("librarianDto", LibrarianDTO.getInstance());
        model.addAttribute("passwordDto", new PasswordDto("", ""));
        return "admin/librarian/admin_add_librarian";
    }

    @PostMapping("/add")
    public String addLibrarian(@ModelAttribute("librarianDto") LibrarianDTO dto,
                               @ModelAttribute("passwordDto") PasswordDto passwordDto) {
        //TODO: check passwords match
        Librarian librarian = new Librarian();
        librarian.setUsername(dto.username());
        librarian.setPassword(encoder.encode(passwordDto.password()));
        librarian.setUserRole(Role.LIBRARIAN);

        Address address = new Address();
        address = addressService.save(address);

        PersonDetails details = PersonDetails.builder()
                .name(dto.name())
                .surname(dto.surname())
                .paternity(dto.paternity())
                .gender(Gender.valueOf(dto.gender()))
                .dateOfBirth(LocalDate.parse(dto.dateOfBirth(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .mobilePhone(dto.mobilePhone())
                .build();
        details = personDetailsService.save(details);

        librarian.setPersonDetails(details);
        librarianService.save(librarian);

        return "redirect:/admin/librarians";
    }

    @PostMapping("/{id}/resetPassword")
    public String resetPassword(@PathVariable String id) {
        Librarian librarian = librarianService.getOneById(id).get();
        librarian.setPassword(encoder.encode("temporary"));
        librarianService.save(librarian);
        return "redirect:/admin/librarians/{id}";
    }

    @PostMapping("/changeStatus")
    public String changeStatus(@RequestParam String id) {
        Librarian librarian = librarianService.getOneById(id).get();
        librarian.setLocked(librarian.isAccountNonLocked());
        librarianService.save(librarian);
        return "redirect:/admin/librarians";
    }

    @GetMapping("/{id}/edit")
    public String editLibrarian(@PathVariable String id, Model model) {
        Librarian librarian = librarianService.getOneById(id).get();
        model.addAttribute("librarianDto", LibrarianDTO.fromLibrarian(librarian, true));
        model.addAttribute("librarianId", librarian.getUserId());
        return "admin/librarian/admin_edit_librarian";
    }

    @PostMapping("/{id}/edit")
    public String editLibrarian(@PathVariable String id, @ModelAttribute("librarian") LibrarianDTO dto) {
        Librarian librarian = librarianService.getOneById(id).get();
        librarian.setUsername(dto.username());
        librarian.getPersonDetails().setName(dto.name());
        librarian.getPersonDetails().setSurname(dto.surname());
        librarian.getPersonDetails().setPaternity(dto.paternity());
        librarian.getPersonDetails().setGender(Gender.valueOf(dto.gender()));
        librarian.getPersonDetails().setDateOfBirth(LocalDate.parse(dto.dateOfBirth(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        librarian.getPersonDetails().setMobilePhone(dto.mobilePhone());
        librarianService.save(librarian);
        return "redirect:/admin/librarians/{id}";
    }

    @PostMapping("/{id}/delete")
    public String deleteLibrarian(@PathVariable String id, Model model) {
        try {
            librarianService.deleteById(id);
        } catch (Exception e) {
            model.addAttribute("errorMsg", "Cannot delete librarian due to: " + e.getCause());
            return "error_details";
        }
        return "redirect:/admin/librarians";
    }
}
