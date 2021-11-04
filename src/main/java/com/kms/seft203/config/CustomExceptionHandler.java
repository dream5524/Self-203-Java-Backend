package com.kms.seft203.config;

import com.kms.seft203.dto.ErrorResponse;
import com.kms.seft203.exception.ContactNotFoundException;
import com.kms.seft203.exception.DashboardDuplicatedException;
import com.kms.seft203.exception.DashboardNotFoundException;
import com.kms.seft203.exception.EmailDuplicatedException;
import com.kms.seft203.exception.EmailNotFoundException;
import com.kms.seft203.exception.TaskNotFoundException;
import com.kms.seft203.exception.VerificationCodeInValidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * When the error occurs, the exception will be thrown and caught by the ExceptionHandler.
 * This class is implemented to customize the ExceptionHandler, including 2 main tasks:
 * 1/ Define an error format for a particular exception
 * 2/ Setup status (404 Not found, 500 Internal server error,...) for response (header + body)
 */
@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    public static final String MODULE_NAME = "DashboardAPI module";

    @ExceptionHandler(EmailDuplicatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEmailDuplicatedException(EmailDuplicatedException e) {
        log.error(MODULE_NAME + ": " + Arrays.toString(e.getStackTrace()));
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(DashboardDuplicatedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDashboardDuplicatedException (DashboardDuplicatedException e) {
        log.error(MODULE_NAME + ": " + Arrays.toString(e.getStackTrace()));
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(EmailNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleEmailNotFoundException(EmailNotFoundException e) {
        log.error(MODULE_NAME + ": " + Arrays.toString(e.getStackTrace()));
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(ContactNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleContactNotFoundException(ContactNotFoundException e) {
        log.error(MODULE_NAME + ": " + Arrays.toString(e.getStackTrace()));
        return new ErrorResponse(e.getMessage());
    }
    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleTaskNotFoundException(TaskNotFoundException e) {
        log.error(MODULE_NAME + ": " + Arrays.toString(e.getStackTrace()));
        return new ErrorResponse(e.getMessage());
    }
    @ExceptionHandler(DashboardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleDashboardNotFoundException(DashboardNotFoundException e){
        log.error(MODULE_NAME + ": " + Arrays.toString(e.getStackTrace()));
        return new ErrorResponse(e.getMessage());
    }
    @ExceptionHandler(VerificationCodeInValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse verificationCodeInValidException(VerificationCodeInValidException e){
        log.error(MODULE_NAME + ": " + Arrays.toString(e.getStackTrace()));
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleCommonException(Exception e) {
        log.error(MODULE_NAME + ": " + Arrays.toString(e.getStackTrace()));
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationException(MethodArgumentNotValidException e){
        List<String> errors = new ArrayList<>();
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            fieldErrorList.forEach(error -> {
                    log.error(error.getDefaultMessage());
                    errors.add(error.getDefaultMessage());
            });
        }
        return new ErrorResponse(errors);
    }
}