package com.havrylenko.library.controller.ui;

import com.havrylenko.library.model.entity.Genre;
import com.havrylenko.library.service.GenreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/genres")
public class AdminGenresController {

    private final GenreService genreService;

    public AdminGenresController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public String getAll(Model model) {
        var list = genreService.getAll();
        model.addAttribute("genres", list);
        model.addAttribute("newGenre", new Genre());
        return "admin/genre/admin_genres";
    }

    @PostMapping("add")
    public String add(@ModelAttribute("newGenre") Genre genre) {
        genreService.save(genre);
        return "redirect:/admin/genres";
    }

    @PostMapping("{id}/delete")
    public String remove(@PathVariable Long id) {
        genreService.deleteById(id);
        return "redirect:/admin/genres";
    }
}
