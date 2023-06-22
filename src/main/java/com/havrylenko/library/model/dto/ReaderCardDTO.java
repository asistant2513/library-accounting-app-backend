package com.havrylenko.library.model.dto;

import com.havrylenko.library.model.entity.ReaderCard;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record ReaderCardDTO (String id,
                             String readerId,
                             String librarianId,
                             String issuedOn,
                             String expiresOn) {

    public static ReaderCardDTO fromReaderCard(final ReaderCard card) {
        var readerId = card.getReader() == null ? "": card.getReader().getUserId();
        return new ReaderCardDTO(card.getId(), readerId,
                card.getIssuedBy().getUserId(), card.getIssuedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                card.getExpiresOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
