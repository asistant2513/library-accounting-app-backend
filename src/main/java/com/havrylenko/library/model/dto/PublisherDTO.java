package com.havrylenko.library.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.havrylenko.library.config.json.filter.NullableFilter;
import com.havrylenko.library.model.entity.Publisher;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static java.util.Objects.isNull;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PublisherDTO implements NullableFilter {
    private String id;
    private String name;
    private String country;

    public PublisherDTO(final Publisher publisher) {
        this.id = publisher.getId();
        this.name = publisher.getName();
        this.country = publisher.getCountry();
    }

    @Override
    @JsonIgnore
    public boolean isEmpty() {
        return isNull(id) && isNull(name) && isNull(country);
    }
}
