package com.havrylenko.library.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String text;
    private short rating;

    @ManyToOne
    @JoinColumn(name="bookId", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name="readerId")
    private Reader reader;
}
