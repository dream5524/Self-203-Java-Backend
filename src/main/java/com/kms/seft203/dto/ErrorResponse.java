package com.kms.seft203.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
