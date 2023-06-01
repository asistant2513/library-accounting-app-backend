package com.havrylenko.library.controller;

import com.havrylenko.library.model.entity.Book;
import com.havrylenko.library.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/books")
public class BookController {
    private final BookService service;

    public BookController(final BookService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookByID(@PathVariable final String id) {
        Optional<Book> optionalBook = service.getOneById(id);
        return optionalBook.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody final Book book) {
        return new ResponseEntity<>(service.save(book), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Book> modifyBook(@RequestBody final Book book) {
        return new ResponseEntity<>(service.save(book), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable final String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
