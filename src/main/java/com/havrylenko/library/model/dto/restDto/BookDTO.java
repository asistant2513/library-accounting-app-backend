package com.havrylenko.library.model.dto.restDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.havrylenko.library.config.json.filter.SerializationFilter;
import com.havrylenko.library.model.entity.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

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
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = SerializationFilter.class)
    private GenreDTO genre;
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = SerializationFilter.class)
    private AuthorDTO author;
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = SerializationFilter.class)
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
        if (book.getReviews() != null) {
            this.reviews = book.getReviews().stream().map(ReviewDTO::new).toList();
        }
        if (book.getReader() != null) {
            this.readerId = book.getReader().getUserId();
        }
    }
}
