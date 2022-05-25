package com.dg.demo.controller;

import com.dg.demo.exception.SortingException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

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
    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(SortingException.class)
    public SortingErrorResponse handleException(SortingException e) {
        return new SortingErrorResponse(e.getMessage());
    }


    public record FieldValidationErrorResponse(String fieldName, String message) {
    }

    public record SortingErrorResponse(String message) {
    }
}
