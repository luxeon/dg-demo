package com.dg.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<FieldValidationErrorResponse> handleValidationException(MethodArgumentNotValidException exception) {
        return exception.getFieldErrors().stream()
                .map(fieldError -> new FieldValidationErrorResponse(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(toList());
    }

    @ResponseBody
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public InternalServerErrorResponse handleException(Exception e) {
        log.error("Internal server error.", e);
        return new InternalServerErrorResponse("Internal server error");
    }


    public record FieldValidationErrorResponse(String fieldName, String message) {
    }

    public record InternalServerErrorResponse(String message) {
    }
}
