package com.havrylenko.library.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.havrylenko.library.model.entity.Author;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorDTO {
    private String id;
    private String name;
    private String surname;
    private String country;

    public AuthorDTO(final Author author) {
        this.id = author.getId();
        this.name = author.getName();
        this.surname = author.getSurname();
        this.country = author.getCountry();
    }
}
