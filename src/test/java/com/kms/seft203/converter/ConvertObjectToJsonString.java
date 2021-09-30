package com.kms.seft203.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class ConvertObjectToJsonString {
    private ObjectMapper objectMapper = new ObjectMapper();

    public String convertObjectToJsonString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
