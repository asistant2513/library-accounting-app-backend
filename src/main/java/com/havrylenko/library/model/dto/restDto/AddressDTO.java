package com.havrylenko.library.model.dto.restDto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.havrylenko.library.config.json.filter.NullableFilter;
import com.havrylenko.library.config.json.filter.ZipCodeSerializationFilter;
import com.havrylenko.library.model.entity.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static java.util.Objects.isNull;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDTO implements NullableFilter {
    private String country;
    private String city;
    private String district;
    private String village;
    private String street;
    private String building;
    private String apartment;
    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = ZipCodeSerializationFilter.class)
    private int zipCode;

    public AddressDTO(final Address address) {
        this.country = address.getCountry();
        this.city = address.getCity();
        this.district = address.getDistrict();
        this.village = address.getVillage();
        this.street = address.getStreet();
        this.building = address.getBuilding();
        this.apartment = address.getApartment();
        this.zipCode = address.getZipCode();
    }

    @Override
    @JsonIgnore
    public boolean isEmpty() {
        return  isNull(country) && isNull(city) & isNull(district)
                && isNull(village) && isNull(street) && isNull(building)
                && isNull(apartment) && (zipCode <= 0);
    }
}
