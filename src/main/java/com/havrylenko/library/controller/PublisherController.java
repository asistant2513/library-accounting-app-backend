package com.havrylenko.library.controller;

import com.havrylenko.library.model.entity.Publisher;
import com.havrylenko.library.service.PublisherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/publishers")
public class PublisherController {
    
    private final PublisherService service;
    
    public PublisherController(final PublisherService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Publisher>> getAllPublishers() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publisher> getPublisherByID(@PathVariable final String id) {
        Optional<Publisher> optionalPublisher = service.getOneById(id);
        return optionalPublisher.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Publisher> addPublisher(@RequestBody final Publisher publisher) {
        return new ResponseEntity<>(service.save(publisher), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Publisher> modifyPublisher(@RequestBody final Publisher publisher) {
        return new ResponseEntity<>(service.save(publisher), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable final String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
