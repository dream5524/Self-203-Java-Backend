package com.kms.seft203.config;

import com.kms.seft203.exception.DuplicatedEmailException;
import com.kms.seft203.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice @Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(DuplicatedEmailException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorResponse handlerDuplicatedEmailException(DuplicatedEmailException e) {
        log.error(e.getStackTrace().toString());
        return new ErrorResponse(HttpStatus.NOT_ACCEPTABLE, HttpStatus.NOT_ACCEPTABLE.value(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ErrorResponse handlerCommonException(Exception e) {
        log.error(e.getStackTrace().toString());
        return new ErrorResponse(HttpStatus.EXPECTATION_FAILED, HttpStatus.EXPECTATION_FAILED.value(), e.getMessage());
    }
}