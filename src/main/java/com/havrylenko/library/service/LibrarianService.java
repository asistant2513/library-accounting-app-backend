package com.havrylenko.library.service;

import com.havrylenko.library.model.entity.Librarian;
import com.havrylenko.library.repository.LibrarianRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class LibrarianService {
    private final LibrarianRepository repository;

    public LibrarianService(final LibrarianRepository repository) {
        this.repository = repository;
    }

    public List<Librarian> getAll() {
        return repository.findAll();
    }

    public Optional<Librarian> getOneById(final String id) {
        return repository.findById(id);
    }

    public Librarian save(final Librarian librarian) {
        return repository.save(librarian);
    }

    public void delete(final Librarian librarian) {
        repository.delete(librarian);
    }

    public void deleteById(final String id) {
        repository.deleteById(id);
    }

    //TODO: add filters for librarians
}
