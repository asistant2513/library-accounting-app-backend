package com.havrylenko.library.model.entity;

import com.havrylenko.library.model.security.User;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.Set;

@Entity
public class Librarian extends User {
    @Getter
    @OneToMany(mappedBy = "issuedBy")
    private Set<ReaderCard> cardsIssued;

    public Librarian() { }

    public Librarian(String username, String password) {
        super(username, password);
    }

    public Librarian(String username, String password, PersonDetails personDetails) {
        super(username, password, personDetails);
    }
}
