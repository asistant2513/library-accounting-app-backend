package com.havrylenko.library.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.havrylenko.library.config.json.filter.NullableFilter;
import com.havrylenko.library.config.json.filter.SerializationFilter;
import com.havrylenko.library.model.entity.PersonDetails;
import com.havrylenko.library.model.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import static java.util.Objects.isNull;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonDetailsDTO implements NullableFilter {
    private String name;
    private String surname;
    private String paternity;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String mobilePhone;
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = SerializationFilter.class)
    private AddressDTO address;

    public PersonDetailsDTO(final PersonDetails details) {
        this.name = details.getName();
        this.surname = details.getSurname();
        this.paternity = details.getPaternity();
        this.gender = details.getGender();
        this.dateOfBirth = details.getDateOfBirth();
        this.mobilePhone = details.getMobilePhone();
        if(details.getAddress() != null) {
            this.address = new AddressDTO(details.getAddress());
        }
    }

    @Override
    @JsonIgnore
    public boolean isEmpty() {
        return isNull(name) && isNull(surname) & isNull(paternity)
                && isNull(gender) && isNull(dateOfBirth)
                && isNull(mobilePhone)&& address.isEmpty();
    }
}
