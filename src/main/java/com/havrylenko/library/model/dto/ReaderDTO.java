package com.havrylenko.library.model.dto;

import com.havrylenko.library.model.entity.Reader;
import com.havrylenko.library.model.enums.Gender;
import com.havrylenko.library.util.JsonConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record ReaderDTO(String id,
                        String username,
                        String readerCardId,
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

    public static ReaderDTO fromReader(final Reader reader, boolean convert) {
        var details = reader.getPersonDetails();
        Gender g = details.getGender();
        String gender;
        if(g == null) {
            gender = Gender.MALE.name();
        } else {
            gender = g.name();
        }
        var dob = details.getDateOfBirth();
        return new ReaderDTO(reader.getUserId(),
                reader.getUsername(),
                reader.getReaderCard().getId(),
                details.getName(),
                details.getSurname(),
                details.getPaternity(),
                gender,
                dob != null ? dob.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "",
                details.getMobilePhone(),
                details.getAddress() != null ? (convert ? JsonConverter.convertAddressToJsonString(details.getAddress()) : details.getAddress().toString()) : "",
                reader.getAccountCreated(),
                reader.getLastLoginTime(),
                reader.getLastPasswordChangedDate(),
                !reader.isAccountNonLocked()
        );
    }

    public static ReaderDTO getInstance() {
        return new ReaderDTO("", "", "", "",
                "", "", "", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), "","", LocalDateTime.now(),
                LocalDateTime.now(), LocalDateTime.now(), true);
    }
}
