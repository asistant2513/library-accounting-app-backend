package com.havrylenko.library.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String country;
    private String city;
    private String district;
    private String village;
    private String street;
    private String building;
    private String apartment;
    private int zipCode;

    @OneToOne(mappedBy = "address")
    private PersonDetails personDetails;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(country != null ? country + " " : "")
                .append(country != null ? country + " " : "")
                .append(city != null ? city + " " : "")
                .append(district != null ? district + " " : "")
                .append(village != null ? village + " " : "")
                .append(building != null ? building : "")
                .append(apartment != null ? "/" + apartment + "," : "")
                .append(zipCode != 0 ? zipCode : "");
        return sb.toString();
    }
}
