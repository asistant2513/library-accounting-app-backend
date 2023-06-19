package com.havrylenko.library.controller.rest;

import com.havrylenko.library.model.dto.restDto.BookDTO;
import com.havrylenko.library.model.entity.Author;
import com.havrylenko.library.model.entity.Book;
import com.havrylenko.library.model.entity.Genre;
import com.havrylenko.library.model.entity.Publisher;
import com.havrylenko.library.service.AuthorService;
import com.havrylenko.library.service.BookService;
import com.havrylenko.library.service.GenreService;
import com.havrylenko.library.service.PublisherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/books")
public class BookController {
    private final BookService service;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final PublisherService publisherService;

    public BookController(final BookService service,
                          final GenreService genreService,
                          final AuthorService authorService,
                          final PublisherService publisherService) {
        this.service = service;
        this.genreService = genreService;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }

    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        return ResponseEntity.ok(service.getAll().stream().map(BookDTO::new).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookByID(@PathVariable final String id) {
        Optional<Book> optionalBook = service.getOneById(id);
        if (optionalBook.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new BookDTO(optionalBook.get()));
    }

    @PostMapping
    public ResponseEntity<BookDTO> addBook(@RequestBody final BookDTO dto) {
        Book book = new Book();
        Optional<Genre> genre = genreService.getOneById(dto.getGenre().getId());
        Optional<Author> author = authorService.getOneById(dto.getAuthor().getId());
        Optional<Publisher> publisher = publisherService.getOneById(dto.getPublisher().getId());

        if(genre.isEmpty() || author.isEmpty() || publisher.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        book.setName(dto.getName());
        book.setGenre(genre.get());
        book.setPages(dto.getPages());
        book.setAuthor(author.get());
        book.setPublisher(publisher.get());
        book.setPublishingYear(dto.getPublishingYear());
        book.setDescription(dto.getDescription());
        book.setDateRegistered(LocalDateTime.now());
        book.setInArchive(false);

        return new ResponseEntity<>(new BookDTO(service.save(book)), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookDTO> modifyBook(@PathVariable final String id,
                                           @RequestBody final Book dto) {
        Optional<Book> optionalBook = service.getOneById(id);
        if(optionalBook.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Genre> genre = genreService.getOneById(dto.getGenre().getId());
        Optional<Author> author = authorService.getOneById(dto.getAuthor().getId());
        Optional<Publisher> publisher = publisherService.getOneById(dto.getPublisher().getId());

        if(genre.isEmpty() || author.isEmpty() || publisher.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        final Book book = optionalBook.get();
        book.setName(dto.getName());
        book.setGenre(genre.get());
        book.setPages(dto.getPages());
        book.setAuthor(author.get());
        book.setPublisher(publisher.get());
        book.setPublishingYear(dto.getPublishingYear());
        book.setDescription(dto.getDescription());
        book.setInArchive(dto.isInArchive());

        return ResponseEntity.ok(new BookDTO(service.save(book)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable final String id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
