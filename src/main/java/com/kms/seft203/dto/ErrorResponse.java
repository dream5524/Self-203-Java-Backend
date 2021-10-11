package com.kms.seft203.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    //them vao dto
    private Date timestamp;
    private HttpStatus status;
    private int statusCode;
    private String message;
}
