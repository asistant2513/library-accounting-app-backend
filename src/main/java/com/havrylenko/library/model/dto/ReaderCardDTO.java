package com.havrylenko.library.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.havrylenko.library.model.entity.ReaderCard;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReaderCardDTO {
    private String id;
    private LocalDate issuedOn;
    private LocalDate expiresOn;
    private String readerId;
    private String issuedBy;

    public ReaderCardDTO(final ReaderCard card) {
        this.id = card.getId();
        this.issuedOn = card.getIssuedOn();
        this.expiresOn = card.getExpiresOn();
        this.readerId = card.getReader().getUserId();
        this.issuedBy = card.getIssuedBy().getUserId();
    }
}
