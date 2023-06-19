package com.havrylenko.library.model.dto.restDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.havrylenko.library.config.json.filter.NullableFilter;
import com.havrylenko.library.model.entity.Genre;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static java.util.Objects.isNull;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenreDTO implements NullableFilter {
    private long id = -1;
    private String name;

    public GenreDTO(final Genre genre) {
        this.id = genre.getId();
        this.name = genre.getName();
    }

    @Override
    @JsonIgnore
    public boolean isEmpty() {
        return id < 0 && isNull(name);
    }
}
