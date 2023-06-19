package com.havrylenko.library.model.dto.restDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.havrylenko.library.config.json.filter.SerializationFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewUserDTO {
    private String username;
    private String password;
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = SerializationFilter.class)
    private PersonDetailsDTO personDetails;

    public NewUserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
