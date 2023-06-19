package com.havrylenko.library.controller.ui;

import com.havrylenko.library.model.dto.LibrarianDTO;
import com.havrylenko.library.model.entity.Librarian;
import com.havrylenko.library.model.enums.Gender;
import com.havrylenko.library.service.LibrarianService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final LibrarianService librarianService;
    private final BCryptPasswordEncoder encoder;

    public AdminController(LibrarianService librarianService,
                           BCryptPasswordEncoder encoder) {
        this.librarianService = librarianService;
        this.encoder = encoder;
    }

    @GetMapping("/librarians")
    public String getLibrariansView(Model model) {
        List<LibrarianDTO> dtos = librarianService.getAll().stream().map(LibrarianDTO::fromLibrarian).toList();
        model.addAttribute("librarians", dtos);
        return "admin/admin_librarians";
    }

    @GetMapping("/librarians/{id}")
    public String getOneLibrarianView(Model model, @PathVariable String id) {
        Librarian librarian = librarianService.getOneById(id).get();
        model.addAttribute("librarian", LibrarianDTO.fromLibrarian(librarian));
        return "admin/admin_single_librarian";
    }

    @PostMapping("/librarians/{id}/resetPassword")
    public String resetPassword(@PathVariable String id) {
        Librarian librarian = librarianService.getOneById(id).get();
        librarian.setPassword(encoder.encode("temporary"));
        librarianService.save(librarian);
        return "redirect:/admin/librarians/{id}";
    }

    @GetMapping("/librarians/{id}/edit")
    public String editLibrarian(@PathVariable String id, Model model) {
        Librarian librarian = librarianService.getOneById(id).get();
        model.addAttribute("librarianDto", LibrarianDTO.fromLibrarian(librarian));
        model.addAttribute("librarianId", librarian.getUserId());
        return "admin/admin_edit_librarian";
    }

    @PostMapping("/librarians/{id}/edit")
    public String editLibrarian(@PathVariable String id, @ModelAttribute("librarian") LibrarianDTO dto) {
        Librarian librarian = librarianService.getOneById(id).get();
        librarian.setUsername(dto.username());
        librarian.getPersonDetails().setName(dto.name());
        librarian.getPersonDetails().setSurname(dto.surname());
        librarian.getPersonDetails().setPaternity(dto.paternity());
        librarian.getPersonDetails().setGender(Gender.valueOf(dto.gender()));
        librarian.getPersonDetails().setDateOfBirth(dto.dateOfBirth());
        librarian.getPersonDetails().setMobilePhone(dto.mobilePhone());
        librarianService.save(librarian);
        return "redirect:/admin/librarians/{id}";
    }

    @PostMapping("/librarians/{id}/delete")
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
