package com.havrylenko.library.controller;

import com.havrylenko.library.model.dto.GenreDTO;
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
    public ResponseEntity<List<GenreDTO>> getAllGenres() {
        return ResponseEntity.ok(service.getAll().stream().map(GenreDTO::new).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDTO> getGenreByID(@PathVariable final Long id) {
        Optional<Genre> optionalGenre = service.getOneById(id);
        if (optionalGenre.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new GenreDTO(optionalGenre.get()));
    }

    @PostMapping
    public ResponseEntity<GenreDTO> addGenre(@RequestBody final GenreDTO dto) {
        Genre genre = new Genre();
        genre.setName(dto.getName());
        return new ResponseEntity<>(new GenreDTO(service.save(genre)), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GenreDTO> modifyGenre(@PathVariable long id,
                                             @RequestBody final GenreDTO dto) {
        Optional<Genre> optional = service.getOneById(id);
        if(optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Genre genre = optional.get();
        genre.setName(dto.getName());
        return new ResponseEntity<>(new GenreDTO(service.save(genre)), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable final Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
