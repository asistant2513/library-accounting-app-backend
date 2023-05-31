package com.havrylenko.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.Year;

@Entity
@Data
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
    private String description;
    private boolean isPresent;
    private LocalDateTime dateRegistered;

    //TODO: add relations to Publisher, Reviews, Reader
    @ManyToOne
    @JoinColumn(name="id", nullable = false)
    private Genre genre;

    @ManyToOne
    @JoinColumn(name="id", nullable = false)
    private Author author;


}
