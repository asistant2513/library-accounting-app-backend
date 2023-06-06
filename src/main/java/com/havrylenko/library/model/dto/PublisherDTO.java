package com.havrylenko.library.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.havrylenko.library.model.entity.Publisher;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PublisherDTO {
    private String id;
    private String name;
    private String country;

    public PublisherDTO(final Publisher publisher) {
        this.id = publisher.getId();
        this.name = publisher.getName();
        this.country = publisher.getCountry();
    }
}
