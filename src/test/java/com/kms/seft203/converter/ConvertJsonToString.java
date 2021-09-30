package com.kms.seft203.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class ConvertJsonToString {
    public String convertJsonToString(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
