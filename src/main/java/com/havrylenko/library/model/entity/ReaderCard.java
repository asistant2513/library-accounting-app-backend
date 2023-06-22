package com.havrylenko.library.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReaderCard {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate issuedOn;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate expiresOn;

    @OneToOne(mappedBy = "readerCard")
    private Reader reader;

    @ManyToOne
    @JoinColumn(name="issuer_id", nullable = false)
    private Librarian issuedBy;

}
