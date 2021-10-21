package com.kms.seft203.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class ErrorResponse {
    private Date timestamp;
    private List<String> messages;

    public ErrorResponse(String message) {
        this.timestamp = new Date();
        this.messages = new ArrayList<>();
        this.messages.add(message);
    }

    public ErrorResponse(List<String> messages) {
        this.timestamp = new Date();
        this.messages = messages;
    }
}
