package com.havrylenko.library.service;

import com.havrylenko.library.model.entity.Genre;
import com.havrylenko.library.repository.GenreRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class GenreService {
    private final GenreRepository repository;

    public GenreService(final GenreRepository repository) {
        this.repository = repository;
    }

    public List<Genre> getAll() {
        return repository.findAll();
    }

    public Optional<Genre> getOneById(final long id) {
        return repository.findById(id);
    }

    public Genre save(final Genre genre) {
        return repository.save(genre);
    }

    public void delete(final Genre genre) {
        repository.delete(genre);
    }

    public void deleteById(final Long id) {
        repository.deleteById(id);
    }

    //TODO: add filters for genres
}
