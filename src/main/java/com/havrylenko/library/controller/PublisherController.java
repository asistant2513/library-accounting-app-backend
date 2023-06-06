package com.havrylenko.library.controller;

import com.havrylenko.library.model.dto.PublisherDTO;
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
    public ResponseEntity<List<PublisherDTO>> getAllPublishers() {
        return ResponseEntity.ok(service.getAll().stream().map(PublisherDTO::new).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherDTO> getPublisherByID(@PathVariable final String id) {
        Optional<Publisher> optionalPublisher = service.getOneById(id);
        if(optionalPublisher.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new PublisherDTO(optionalPublisher.get()));
    }

    @PostMapping
    public ResponseEntity<PublisherDTO> addPublisher(@RequestBody final PublisherDTO dto) {
        Publisher publisher = new Publisher();
        publisher.setName(dto.getName());
        publisher.setCountry(dto.getCountry());

        return new ResponseEntity<>(new PublisherDTO(service.save(publisher)), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PublisherDTO> modifyPublisher(@PathVariable final String id,
                                                        @RequestBody final PublisherDTO dto) {
        Optional<Publisher> optionalPublisher = service.getOneById(id);
        if(optionalPublisher.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Publisher publisher = optionalPublisher.get();
        publisher.setName(dto.getName());
        publisher.setCountry(dto.getCountry());

        return ResponseEntity.ok(new PublisherDTO(publisher));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublisher(@PathVariable final String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
