package com.havrylenko.library.model.entity;

import com.havrylenko.library.model.security.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
public class Reader extends User {
    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="card_id", nullable = false)
    private ReaderCard readerCard;

    @Getter
    @OneToMany(mappedBy = "reader")
    private Set<Book> books;

    public Reader(String username, String password) {
        super(username, password);
    }

    public Reader(String username, String password, PersonDetails details, ReaderCard readerCard) {
        super(username, password, details);
        this.readerCard = readerCard;
    }
}
