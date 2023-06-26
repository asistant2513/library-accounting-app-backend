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
                .country(json.getString("country"))
                .city(json.getString("city"))
                .district(json.getString("district"))
                .village(json.getString("village"))
                .street(json.getString("street"))
                .building(json.getString("building"))
                .apartment(json.getString("apartment"))
                .zipCode(json.getInt("zipcode"))
                .build();
    }
}
