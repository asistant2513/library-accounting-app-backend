package com.havrylenko.library.config.json.filter;

public class ZipCodeSerializationFilter {
    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return true;
        int value = (Integer)obj;
        return value <= 0;
    }
}
