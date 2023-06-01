package com.havrylenko.library.controller;

import com.havrylenko.library.model.entity.Author;
import com.havrylenko.library.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/authors")
public class AuthorController {
    
    private final AuthorService service;
    
    public AuthorController(final AuthorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAllAuthors() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorByID(@PathVariable final String id) {
        Optional<Author> optionalAuthor = service.getOneById(id);
        return optionalAuthor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Author> addAuthor(@RequestBody final Author author) {
        return new ResponseEntity<>(service.save(author), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Author> modifyAuthor(@RequestBody final Author author) {
        return new ResponseEntity<>(service.save(author), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable final String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
