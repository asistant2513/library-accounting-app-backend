package com.havrylenko.library.controller.ui;

import com.havrylenko.library.model.dto.ReaderDTO;
import com.havrylenko.library.model.entity.Reader;
import com.havrylenko.library.model.enums.Gender;
import com.havrylenko.library.service.AddressService;
import com.havrylenko.library.service.PersonDetailsService;
import com.havrylenko.library.service.ReaderService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/admin/readers")
public class AdminReadersController {
    private final ReaderService readerService;
    private final PersonDetailsService personDetailsService;
    private final AddressService addressService;
    private final BCryptPasswordEncoder encoder;

    public AdminReadersController(ReaderService readerService,
                                  PersonDetailsService personDetailsService,
                                  AddressService addressService,
                                  BCryptPasswordEncoder encoder) {
        this.readerService = readerService;
        this.personDetailsService = personDetailsService;
        this.addressService = addressService;
        this.encoder = encoder;
    }

    @GetMapping
    public String getAll(Model model) {
        var list = readerService.getAll().stream().map( r -> ReaderDTO.fromReader(r, false));
        model.addAttribute("readers", list);
        return "admin/reader/admin_readers";
    }

    @GetMapping("{id}")
    public String getById(Model model, @PathVariable String id) {
        Reader reader = readerService.getOneById(id).get();
        model.addAttribute("reader", ReaderDTO.fromReader(reader,false));
        return "admin/reader/admin_single_reader";
    }

    @GetMapping("{id}/edit")
    public String edit(Model model, @PathVariable String id) {
        Reader reader = readerService.getOneById(id).get();
        model.addAttribute("reader", ReaderDTO.fromReader(reader, true));
        model.addAttribute("readerId", reader.getUserId());
        return "admin/reader/admin_edit_reader";
    }

    @PostMapping("{id}/edit")
    public String edit(@ModelAttribute("reader") ReaderDTO dto, @PathVariable String id) {
        Reader reader = readerService.getOneById(id).get();
        reader.setUsername(dto.username());
        reader.getPersonDetails().setName(dto.name());
        reader.getPersonDetails().setSurname(dto.surname());
        reader.getPersonDetails().setPaternity(dto.paternity());
        reader.getPersonDetails().setGender(Gender.valueOf(dto.gender()));
        reader.getPersonDetails().setDateOfBirth(LocalDate.parse(dto.dateOfBirth(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        reader.getPersonDetails().setMobilePhone(dto.mobilePhone());
        readerService.save(reader);
        return "redirect:/admin/readers/{id}";
    }

    @PostMapping("/{id}/delete")
    public String deleteLibrarian(@PathVariable String id, Model model) {
        var reader = readerService.getOneById(id).get();
        try {
            if(!reader.getBooks().isEmpty()) {
                throw new RuntimeException("Reader has books on hands");
            }
            readerService.deleteById(id);
        } catch (Exception e) {
            model.addAttribute("errorMsg", "Cannot delete reader due to: " + e.getCause());
            return "error_details";
        }
        return "redirect:/admin/readers";
    }

    @PostMapping("/{id}/resetPassword")
    public String resetPassword(@PathVariable String id) {
        Reader reader = readerService.getOneById(id).get();
        reader.setPassword(encoder.encode("temporary"));
        readerService.save(reader);
        return "redirect:/admin/readers/{id}";
    }

    @PostMapping("/changeStatus")
    public String changeStatus(@RequestParam String id) {
        Reader reader = readerService.getOneById(id).get();
        reader.setLocked(reader.isAccountNonLocked());
        readerService.save(reader);
        return "redirect:/admin/readers";
    }
}
