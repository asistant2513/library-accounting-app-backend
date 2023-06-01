package com.havrylenko.library.service;

import com.havrylenko.library.model.entity.Reader;
import com.havrylenko.library.repository.ReaderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class ReaderService {
    private final ReaderRepository repository;

    public ReaderService(final ReaderRepository repository) {
        this.repository = repository;
    }

    public List<Reader> getAll() {
        return repository.findAll();
    }

    public Optional<Reader> getOneById(final String id) {
        return repository.findById(id);
    }

    public Reader save(final Reader reader) {
        return repository.save(reader);
    }

    public void delete(final Reader reader) {
        repository.delete(reader);
    }

    public void deleteById(final String id) {
        repository.deleteById(id);
    }

    //TODO: add filters for reader reader
}
