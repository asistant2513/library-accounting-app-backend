package com.havrylenko.library.config.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.havrylenko.library.model.entity.Librarian;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component("librarianMapper")
public class LibrarianMapper implements JsonMapper<Librarian> {

    @Override
    public Librarian mapToObject(final JsonNode node) {
        String username = node.get("username").asText();
        String password = node.get("password").asText();
        if(!Objects.isNull(node.get("personDetails"))) {
            //TODO: add personDetails mapping
        }
        Librarian librarian = new Librarian(username, password);

        return librarian;
    }
}
