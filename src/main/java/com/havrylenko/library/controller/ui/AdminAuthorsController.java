package com.havrylenko.library.controller.ui;

import com.havrylenko.library.model.entity.Author;
import com.havrylenko.library.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/authors")
public class AdminAuthorsController {
    private final AuthorService authorService;

    public AdminAuthorsController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public String getAll(Model model) {
        var list = authorService.getAll();
        model.addAttribute("authors", list);
        model.addAttribute("newAuthor", new Author());
        return "admin/author/admin_authors";
    }

    @PostMapping("add")
    public String add(@ModelAttribute("newAuthor") Author author) {
        authorService.save(author);
        return "redirect:/admin/authors";
    }

    @PostMapping("{id}/delete")
    public String remove(@PathVariable String id) {
        authorService.deleteById(id);
        return "redirect:/admin/authors";
    }
}
