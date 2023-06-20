package com.havrylenko.library.model.dto;

import com.havrylenko.library.model.entity.ReaderCard;

import java.time.LocalDate;

public record ReaderCardDTO (String id,
                             String readerId,
                             String librarianId,
                             LocalDate issuedOn,
                             LocalDate expiresOn) {

    public static ReaderCardDTO fromReaderCard(final ReaderCard card) {
        return new ReaderCardDTO(card.getId(), card.getReader().getUserId(),
                card.getIssuedBy().getUserId(), card.getIssuedOn(), card.getExpiresOn());
    }
}
