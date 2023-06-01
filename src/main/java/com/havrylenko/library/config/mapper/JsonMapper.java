package com.havrylenko.library.config.mapper;

import com.fasterxml.jackson.databind.JsonNode;

public interface JsonMapper <T> {
    T mapToObject(final JsonNode node);
}
