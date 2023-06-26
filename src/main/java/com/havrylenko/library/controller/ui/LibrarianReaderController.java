package com.havrylenko.library.controller.ui;

import com.havrylenko.library.model.dto.ReaderDTO;
import com.havrylenko.library.model.entity.Reader;
import com.havrylenko.library.service.ReaderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.Period;

@Controller
@RequestMapping("/library/readers")
public class LibrarianReaderController {
    private final ReaderService readerService;

    public LibrarianReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }

    @GetMapping
    public String getAll(Model model,
                         @RequestParam(required = false) boolean defaulters,
                         @RequestParam(required = false) boolean inactive) {
        var readersList = readerService.getAll();
        if (defaulters) {
            readersList = readersList.stream().filter(
                    r -> r.getBooks().stream()
                            .anyMatch(b -> b.getReservedTill().isAfter(LocalDate.now()))
            ).toList();
        }
        if (inactive) {
            readersList = readersList.stream().filter(
                    r -> {
                        if (r.getLastLoginTime() == null)
                            return true;
                        return Period.between(r.getLastLoginTime().toLocalDate(), LocalDate.now()).toTotalMonths() > 3;
                    })
            .toList();
        }
        var list = readersList.stream().map(r -> ReaderDTO.fromReader(r, false));
        model.addAttribute("readers", list);
        return "librarian/librarian_readers";
    }

    @GetMapping("{id}")
    public String getById(Model model, @PathVariable String id) {
        Reader reader = readerService.getOneById(id).get();
        model.addAttribute("reader", ReaderDTO.fromReader(reader, false));
        return "librarian/librarian_single_reader";
    }
}
