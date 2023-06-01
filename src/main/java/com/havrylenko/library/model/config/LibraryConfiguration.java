package com.havrylenko.library.model.config;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class LibraryConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short id;
    private long accountExpirationPeriod;
    private long passwordExpirationPeriod;
    private long daysToReturnBook;
}
