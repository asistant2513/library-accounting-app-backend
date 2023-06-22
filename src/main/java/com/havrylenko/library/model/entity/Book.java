package com.havrylenko.library.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private Year publishingYear;
    private int pages;
    @Column(columnDefinition = "text", length = 65535)
    private String description;
    private LocalDateTime dateRegistered;
    private boolean isInArchive;
    private int timesBooked;
    private LocalDate reservedTill;

    @ManyToOne
    @JoinColumn(name="genreId", nullable = false)
    private Genre genre;

    @ManyToOne
    @JoinColumn(name="authorId", nullable = false)
    private Author author;

    @ManyToOne
    @JoinColumn(name="publisherId", nullable = false)
    private Publisher publisher;

    @OneToMany(mappedBy = "book")
    private Set<Review> reviews;

    @ManyToOne
    @JoinColumn(name="readerId")
    private Reader reader;
}
