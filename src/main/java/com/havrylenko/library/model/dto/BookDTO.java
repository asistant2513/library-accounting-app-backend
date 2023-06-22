package com.havrylenko.library.model.dto;

import com.havrylenko.library.model.entity.Book;

import java.time.Year;
import java.time.format.DateTimeFormatter;

public record BookDTO (String id,
                       String name,
                       int year,
                       int pages,
                       String description,
                       String registeredOn,
                       boolean archived,
                       int timesBooked,
                       String genre,
                       long genreId,
                       String author,
                       String authorId,
                       String publisher,
                       String publisherId,
                       String readerId) {

    public static BookDTO getInstance() {
        return new BookDTO("","", Year.now().getValue(), 0, "", "", false,
                0,"", 0, "", "", "", "", "");
    }

    public static BookDTO fromBook(final Book book) {
        String desc = book.getDescription().length() > 50 ? book.getDescription().substring(0, 50) + "..." : book.getDescription();
        return new BookDTO(book.getId(),book.getName(), book.getPublishingYear().getValue(),
                book.getPages(), desc, book.getDateRegistered().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                book.isInArchive(), book.getTimesBooked(),book.getGenre().getName(), book.getGenre().getId(),
                book.getAuthor().toString(), book.getAuthor().getId(), book.getPublisher().toString(), book.getPublisher().getId(),
                book.getReader() == null ? "" : book.getReader().getUserId());
    }

    public static BookDTO fromBook(final Book book, boolean fullDescription) {
        String desc = book.getDescription();
        if(!fullDescription){
            desc = book.getDescription().length() > 50 ? book.getDescription().substring(0, 50) + "..." : book.getDescription();
        }
        return new BookDTO(book.getId(),book.getName(), book.getPublishingYear().getValue(),
                book.getPages(), desc, book.getDateRegistered().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                book.isInArchive(), book.getTimesBooked(),book.getGenre().getName(), book.getGenre().getId(),
                book.getAuthor().toString(), book.getAuthor().getId(), book.getPublisher().toString(), book.getPublisher().getId(),
                book.getReader() == null ? "" : book.getReader().getUserId());
    }
}
