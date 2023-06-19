package com.havrylenko.library.controller.rest;

import com.havrylenko.library.model.dto.restDto.AuthorDTO;
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
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        return ResponseEntity.ok(service.getAll().stream().map(AuthorDTO::new).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorByID(@PathVariable final String id) {
        Optional<Author> optionalAuthor = service.getOneById(id);
        if (optionalAuthor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new AuthorDTO(optionalAuthor.get()));
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> addAuthor(@RequestBody final AuthorDTO dto) {
        Author author = new Author();
        author.setName(dto.getName());
        author.setSurname(dto.getSurname());
        author.setCountry(dto.getCountry());

        return new ResponseEntity<>(new AuthorDTO(service.save(author)), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AuthorDTO> modifyAuthor(@PathVariable final String id,
                                               @RequestBody final AuthorDTO dto) {
        Optional<Author> optionalAuthor = service.getOneById(id);
        if(optionalAuthor.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Author author = optionalAuthor.get();
        author.setName(dto.getName());
        author.setSurname(dto.getSurname());
        author.setCountry(dto.getCountry());

        return ResponseEntity.ok(new AuthorDTO(service.save(author)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable final String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
