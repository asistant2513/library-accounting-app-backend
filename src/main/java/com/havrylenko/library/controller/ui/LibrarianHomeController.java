package com.havrylenko.library.controller.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/library")
public class LibrarianHomeController {
    @GetMapping
    public String get() {
        return "/librarian/librarian_menu";
    }
}
