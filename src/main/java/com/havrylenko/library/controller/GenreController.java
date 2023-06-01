package com.havrylenko.library.controller;

import com.havrylenko.library.model.entity.Genre;
import com.havrylenko.library.service.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/genres")
public class GenreController {
    
    private GenreService service;
    
    public GenreController(final GenreService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Genre>> getAllGenres() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenreByID(@PathVariable final Long id) {
        Optional<Genre> optionalGenre = service.getOneById(id);
        return optionalGenre.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Genre> addGenre(@RequestBody final Genre genre) {
        return new ResponseEntity<>(service.save(genre), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Genre> modifyGenre(@RequestBody final Genre genre) {
        return new ResponseEntity<>(service.save(genre), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable final Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
