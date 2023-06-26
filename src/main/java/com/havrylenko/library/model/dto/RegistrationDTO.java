package com.havrylenko.library.model.dto;

import java.time.LocalDate;

public record RegistrationDTO( String username,
                               String password,
                               String repeatPassword,
                               String name,
                               String surname,
                               String paternity,
                               String gender,
                               LocalDate dateOfBirth,
                               String address,
                               String mobilePhone,
                               String librarianId) {

    public static RegistrationDTO getInstance() {
        return new RegistrationDTO("", "", "",
                "", "", "", "", LocalDate.now(),"",
                "", "");
    }
}
