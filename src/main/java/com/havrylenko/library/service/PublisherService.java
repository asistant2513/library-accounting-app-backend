package com.havrylenko.library.service;

import com.havrylenko.library.model.entity.Publisher;
import com.havrylenko.library.repository.PublisherRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class PublisherService {
    private final PublisherRepository repository;

    public PublisherService(final PublisherRepository repository) {
        this.repository = repository;
    }

    public List<Publisher> getAll() {
        return repository.findAll();
    }

    public Optional<Publisher> getOneById(final String id) {
        return repository.findById(id);
    }

    public Publisher save(final Publisher librarian) {
        return repository.save(librarian);
    }

    public void delete(final Publisher librarian) {
        repository.delete(librarian);
    }

    public void deleteById(final String id) {
        repository.deleteById(id);
    }

    //TODO: add filters for publishers
}
