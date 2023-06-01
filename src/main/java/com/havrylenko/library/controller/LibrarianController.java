package com.havrylenko.library.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.havrylenko.library.config.mapper.JsonMapper;
import com.havrylenko.library.model.entity.Librarian;
import com.havrylenko.library.service.LibrarianService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/librarians")
public class LibrarianController {
    private final LibrarianService service;
    private final JsonMapper<Librarian> librarianMapper;

    public LibrarianController(final LibrarianService service,
                               @Qualifier("librarianMapper") final JsonMapper<Librarian> mapper) {
        this.service = service;
        this.librarianMapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<Librarian>> getAllLibrarians() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Librarian> getLibrarianByID(@PathVariable final String id) {
        Optional<Librarian> optionalLibrarian = service.getOneById(id);
        return optionalLibrarian.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Librarian> addLibrarian(@RequestBody final JsonNode json) {
        final Librarian librarian = librarianMapper.mapToObject(json);
        return new ResponseEntity<>(service.save(librarian), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Librarian> modifyLibrarian(@RequestBody final Librarian librarian) {
        return new ResponseEntity<>(service.save(librarian), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibrarian(@PathVariable final String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
