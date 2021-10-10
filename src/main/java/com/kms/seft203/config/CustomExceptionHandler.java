package com.kms.seft203.config;

import com.kms.seft203.exception.DuplicatedEmailException;
import com.kms.seft203.exception.EmailNotFoundException;
import com.kms.seft203.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.Date;

/**
 * When the error occurs, the exception will be thrown and caught by the ExceptionHandler.
 * This class is implemented to customize the ExceptionHandler, including 2 main tasks:
 * 1/ Define an error format for a particular exception
 * 2/ Setup status (404 Not found, 500 Internal server error,...) for response (header + body)
 */
@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {
    @ExceptionHandler(DuplicatedEmailException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorResponse handlerDuplicatedEmailException(DuplicatedEmailException e) {
        log.error("From dashboardAPI module: " + Arrays.toString(e.getStackTrace()));
        return new ErrorResponse(new Date(), HttpStatus.NOT_ACCEPTABLE, HttpStatus.NOT_ACCEPTABLE.value(), e.getMessage());
    }

    @ExceptionHandler(EmailNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerEmailNotFoundException(EmailNotFoundException e) {
        log.error("From dashboardAPI module: " + Arrays.toString(e.getStackTrace()));
        return new ErrorResponse(new Date(), HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handlerCommonException(Exception e) {
        log.error("From dashboardAPI module: " + Arrays.toString(e.getStackTrace()));
        return new ErrorResponse(new Date(), HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}