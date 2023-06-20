package com.havrylenko.library.controller.ui;

import com.havrylenko.library.model.dto.LibrarianSelectDTO;
import com.havrylenko.library.model.dto.ReaderCardDTO;
import com.havrylenko.library.service.LibrarianService;
import com.havrylenko.library.service.ReaderCardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Controller
@RequestMapping("admin/cards")
public class AdminCardsController {
    private final ReaderCardService readerCardService;
    private final LibrarianService librarianService;

    public AdminCardsController(ReaderCardService readerCardService,
                                LibrarianService librarianService) {
        this.readerCardService = readerCardService;
        this.librarianService = librarianService;
    }

    @GetMapping
    public String getAll(Model model) {
        var list = readerCardService.getAll().stream().map(ReaderCardDTO::fromReaderCard).toList();
        model.addAttribute("cards", list);
        return "admin/readerCard/admin_cards";
    }

    @GetMapping("{id}")
    public String getOne(Model model, @PathVariable String id) {
        var card = readerCardService.getOneById(id).get();
        model.addAttribute("card", ReaderCardDTO.fromReaderCard(card));
        return "admin/readerCard/admin_single_card";
    }

    @GetMapping("{id}/edit")
    public String edit(Model model, @PathVariable String id) {
        var card = readerCardService.getOneById(id).get();
        var librarians = librarianService.getAll().stream()
                .map( l -> new LibrarianSelectDTO(l.getUserId(),
                        String.format("%s %s %s",
                                l.getPersonDetails().getName(),
                                l.getPersonDetails().getSurname(),
                                l.getPersonDetails().getPaternity())))
                .toList();
        model.addAttribute("card", ReaderCardDTO.fromReaderCard(card));
        model.addAttribute("librarians", librarians);
        return "admin/readerCard/admin_edit_card";
    }

    @PostMapping("{id}/edit")
    public String edit(@ModelAttribute("card") ReaderCardDTO dto, @PathVariable String id) {
        var card = readerCardService.getOneById(id).get();
        var librarian = librarianService.getOneById(dto.librarianId()).get();
        if (!card.getIssuedBy().equals(librarian)) {
            card.setIssuedBy(librarian);
        }
        card.setExpiresOn(LocalDate.parse(dto.expiresOn(),DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        readerCardService.save(card);
        return "redirect:/admin/cards/{id}";
    }
}
