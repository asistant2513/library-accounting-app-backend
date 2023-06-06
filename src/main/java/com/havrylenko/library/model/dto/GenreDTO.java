package com.havrylenko.library.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.havrylenko.library.model.entity.Genre;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenreDTO {
    private long id;
    private String name;

    public GenreDTO(final Genre genre) {
        this.id = genre.getId();
        this.name = genre.getName();
    }
}
