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
        return new ReaderCardDTO(card.getId(), card.getReader().getUserId(),
                card.getIssuedBy().getUserId(), card.getIssuedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                card.getExpiresOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
}
