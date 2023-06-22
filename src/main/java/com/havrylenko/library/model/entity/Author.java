package com.havrylenko.library.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String name;
    private String surname;
    private String country;

    @OneToMany(mappedBy = "author")
    private Set<Book> books;

    @Override
    public String toString() {
        return String.format("%s %s, %s", surname, name, country);
    }
}
