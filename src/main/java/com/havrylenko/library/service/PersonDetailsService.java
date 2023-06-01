package com.havrylenko.library.service;

import com.havrylenko.library.model.entity.PersonDetails;
import com.havrylenko.library.repository.PersonDetailsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class PersonDetailsService {
    private final PersonDetailsRepository repository;

    public PersonDetailsService(final PersonDetailsRepository repository) {
        this.repository = repository;
    }

    public List<PersonDetails> getAll() {
        return repository.findAll();
    }

    public Optional<PersonDetails> getOneById(final Long id) {
        return repository.findById(id);
    }

    public PersonDetails save(final PersonDetails details) {
        return repository.save(details);
    }

    public void delete(final PersonDetails details) {
        repository.delete(details);
    }

    public void deleteById(final Long id) {
        repository.deleteById(id);
    }
}
