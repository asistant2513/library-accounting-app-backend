package com.havrylenko.library.service;

import com.havrylenko.library.model.entity.ReaderCard;
import com.havrylenko.library.repository.ReaderCardRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class ReaderCardService {
    private final ReaderCardRepository repository;

    public ReaderCardService(final ReaderCardRepository repository) {
        this.repository = repository;
    }

    public List<ReaderCard> getAll() {
        return repository.findAll();
    }

    public Optional<ReaderCard> getOneById(final String id) {
        return repository.findById(id);
    }

    public ReaderCard save(final ReaderCard card) {
        return repository.save(card);
    }

    public void delete(final ReaderCard card) {
        repository.delete(card);
    }

    public void deleteById(final String id) {
        repository.deleteById(id);
    }

    //TODO: add filters for reader cards
}
