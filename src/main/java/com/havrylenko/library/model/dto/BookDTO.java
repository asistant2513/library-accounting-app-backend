package com.havrylenko.library.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.havrylenko.library.model.entity.*;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDTO {
    private String id;
    private String name;
    private Year publishingYear;
    private int pages;
    private String description;
    private LocalDateTime dateRegistered;
    private boolean isInArchive;
    private GenreDTO genre;
    private AuthorDTO author;
    private PublisherDTO publisher;
    private List<ReviewDTO> reviews;
    private String readerId;

    public BookDTO(final Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.publishingYear = book.getPublishingYear();
        this.pages = book.getPages();
        this.description = book.getDescription();
        this.dateRegistered = book.getDateRegistered();
        this.isInArchive = book.isInArchive();
        this.genre = new GenreDTO(book.getGenre());
        this.author = new AuthorDTO(book.getAuthor());
        this.publisher = new PublisherDTO(book.getPublisher());
        this.reviews = book.getReviews().stream().map(ReviewDTO::new).toList();
        this.readerId = book.getReader().getUserId();
    }
}
