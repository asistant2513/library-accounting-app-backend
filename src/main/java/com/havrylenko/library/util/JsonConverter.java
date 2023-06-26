package com.havrylenko.library.util;

import com.havrylenko.library.model.entity.Address;
import org.apache.logging.log4j.util.Strings;
import org.json.JSONObject;

public final class JsonConverter {
    public static Address convertJsonToAddress(String string) {
        if(Strings.isBlank(string)){
            return new Address();
        }
        JSONObject json = new JSONObject(string);
        return Address.builder()
                .country(json.has("country") ? json.getString("country") : "")
                .city(json.has("city") ? json.getString("city") : "")
                .district(json.has("district") ? json.getString("district") : "")
                .village(json.has("village") ? json.getString("village") : "")
                .street(json.has("street") ? json.getString("street") : "")
                .building(json.has("building") ? json.getString("building") : "")
                .apartment(json.has("apartment") ? json.getString("apartment") : "")
                .zipCode(json.has("zipcode") ? json.getInt("zipcode") : 0)
                .build();
    }

    public static String convertAddressToJsonString(final Address address) {
        JSONObject json = new JSONObject();
        return  json.accumulate("country", address.getCountry())
                .accumulate("city", address.getCity())
                .accumulate("district", address.getDistrict())
                .accumulate("village", address.getVillage())
                .accumulate("street", address.getStreet())
                .accumulate("building", address.getBuilding())
                .accumulate("apartment", address.getApartment())
                .accumulate("zipcode", address.getZipCode())
                .toString();
    }
}
