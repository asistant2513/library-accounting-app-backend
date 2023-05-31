package com.havrylenko.library.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReaderCard {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private LocalDate issuedOn;
    private LocalDate expiresOn;

    @OneToOne(mappedBy = "readerCard")
    private Reader reader;

    @ManyToOne
    @JoinColumn(name="issuer_id", nullable = false)
    private Librarian issuedBy;

}
