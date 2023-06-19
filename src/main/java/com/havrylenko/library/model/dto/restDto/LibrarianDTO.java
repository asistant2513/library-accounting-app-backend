package com.havrylenko.library.model.dto.restDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.havrylenko.library.config.json.filter.SerializationFilter;
import com.havrylenko.library.model.entity.Librarian;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LibrarianDTO {
    private UserDTO userDetails;
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = SerializationFilter.class)
    private PersonDetailsDTO personDetails;

    public LibrarianDTO(final Librarian librarian) {
        this.userDetails = new UserDTO(librarian);
        this.personDetails = new PersonDetailsDTO(librarian.getPersonDetails());
    }
}
