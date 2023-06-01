package com.havrylenko.library.service;

import com.havrylenko.library.model.entity.Book;
import com.havrylenko.library.repository.BookRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class BookService {
    private final BookRepository repository;

    public BookService(final BookRepository repository) {
        this.repository = repository;
    }

    public List<Book> getAll() {
        return repository.findAll();
    }

    public Optional<Book> getOneById(final String id) {
        return repository.findById(id);
    }

    public Book save(final Book book) {
        return repository.save(book);
    }

    public void delete(final Book book) {
        repository.delete(book);
    }

    public void deleteById(final String id) {
        repository.deleteById(id);
    }

    //TODO: add filters for books
}
