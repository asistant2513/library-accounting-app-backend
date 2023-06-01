package com.havrylenko.library.controller;

import com.havrylenko.library.model.entity.Reader;
import com.havrylenko.library.service.ReaderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/readers")
public class ReaderController {
    
    private final ReaderService service;
    
    public ReaderController(final ReaderService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Reader>> getAllReaders() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reader> getReaderByID(@PathVariable final String id) {
        Optional<Reader> optionalReader = service.getOneById(id);
        return optionalReader.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Reader> addReader(@RequestBody final Reader reader) {
        return new ResponseEntity<>(service.save(reader), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Reader> modifyReader(@RequestBody final Reader reader) {
        return new ResponseEntity<>(service.save(reader), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReader(@PathVariable final String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
