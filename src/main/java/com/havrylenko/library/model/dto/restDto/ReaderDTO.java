package com.havrylenko.library.model.dto.restDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.havrylenko.library.config.json.filter.SerializationFilter;
import com.havrylenko.library.model.entity.Reader;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReaderDTO {
    private UserDTO userDetails;
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = SerializationFilter.class)
    private PersonDetailsDTO personDetails;
    private String readerCardId;

    public ReaderDTO(final Reader reader) {
        this.userDetails = new UserDTO(reader);
        this.personDetails = new PersonDetailsDTO(reader.getPersonDetails());
        this.readerCardId = reader.getReaderCard().getId();
    }
}
