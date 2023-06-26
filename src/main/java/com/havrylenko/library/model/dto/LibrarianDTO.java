package com.havrylenko.library.model.dto;

import com.havrylenko.library.model.entity.Librarian;
import com.havrylenko.library.model.enums.Gender;
import com.havrylenko.library.util.JsonConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record LibrarianDTO(String id,
                           String username,
                           String name,
                           String surname,
                           String paternity,
                           String gender,
                           String dateOfBirth,
                           String mobilePhone,
                           String address,
                           LocalDateTime accountCreated,
                           LocalDateTime lastLogin,
                           LocalDateTime lastPasswordChanged,
                           Boolean isLocked) {

    public static LibrarianDTO fromLibrarian(final Librarian librarian, boolean convert) {
        var details = librarian.getPersonDetails();
        Gender g = details.getGender();
        String gender;
        if(g == null) {
            gender = Gender.MALE.name();
        } else {
            gender = g.name();
        }
        var dob = details.getDateOfBirth();
        return new LibrarianDTO(librarian.getUserId(),
                librarian.getUsername(),
                details.getName(),
                details.getSurname(),
                details.getPaternity(),
                gender,
                dob != null ? dob.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "",
                details.getMobilePhone(),
                details.getAddress() != null ? (convert ? JsonConverter.convertAddressToJsonString(details.getAddress()) : details.getAddress().toString()) : "",
                librarian.getAccountCreated(),
                librarian.getLastLoginTime(),
                librarian.getLastPasswordChangedDate(),
                !librarian.isAccountNonLocked()
        );
    }

    public static LibrarianDTO getInstance() {
        return new LibrarianDTO("", "", "",
                "", "", "", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), "","", LocalDateTime.now(),
                LocalDateTime.now(), LocalDateTime.now(), false);
    }
}
