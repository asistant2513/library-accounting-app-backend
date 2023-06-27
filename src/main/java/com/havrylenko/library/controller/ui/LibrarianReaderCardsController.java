package com.havrylenko.library.controller.ui;

import com.havrylenko.library.model.dto.ReaderCardDTO;
import com.havrylenko.library.service.ReaderCardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/library/cards")
public class LibrarianReaderCardsController {
    private final ReaderCardService readerCardService;

    public LibrarianReaderCardsController(ReaderCardService readerCardService) {
        this.readerCardService = readerCardService;
    }

    @GetMapping("{id}")
    public String getById(@PathVariable String id,
                          Model model) {
        var cards = ReaderCardDTO.fromReaderCard(readerCardService.getOneById(id).get());
        model.addAttribute("cards", cards);
        return "librarian/librarian_cards";
    }
}
