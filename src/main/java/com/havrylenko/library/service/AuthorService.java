package com.havrylenko.library.service;

import com.havrylenko.library.model.entity.Author;
import com.havrylenko.library.repository.AuthorRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
//TODO: add logging to services
@Service
@Log4j2
public class AuthorService {
    private final AuthorRepository repository;

    public AuthorService(final AuthorRepository repository) {
        this.repository = repository;
    }

    public List<Author> getAll() {
        log.debug("Getting all authors from DB");
        return repository.findAll();
    }

    public Optional<Author> getOneById(final String id) {
        log.debug("Getting author by id '{}' from DB", id);
        return repository.findById(id);
    }

    public Author save(final Author author) {
        return repository.save(author);
    }

    public void delete(final Author author) {
        repository.delete(author);
    }

    public void deleteById(final String id) {
        repository.deleteById(id);
    }

    //TODO: add some filters for author
}
