package com.havrylenko.library.config.json.filter;

import com.havrylenko.library.model.dto.AddressDTO;

public class SerializationFilter {

    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return true;
        NullableFilter filter = (NullableFilter)obj;
        return filter.isEmpty();
    }
}
