package com.havrylenko.library.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.havrylenko.library.config.json.filter.NullableFilter;
import com.havrylenko.library.model.entity.Author;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static java.util.Objects.isNull;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorDTO implements NullableFilter {
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

    @Override
    @JsonIgnore
    public boolean isEmpty() {
        return isNull(id) && isNull(name) && isNull(surname) && isNull(country);
    }
}
