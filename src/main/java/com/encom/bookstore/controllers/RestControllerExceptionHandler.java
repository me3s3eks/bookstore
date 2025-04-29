package com.encom.bookstore.controllers;

import com.encom.bookstore.exceptions.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestControllerExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ProblemDetail> handleBindException(BindException e,
                                                             Locale locale) {
        String localizedMessage = messageSource.getMessage("errors.error.400.data_is_not_valid",
            null, "errors.error.400.default", locale);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
            localizedMessage);
        problemDetail.setProperty("errors", e.getAllErrors().stream()
            .map(ObjectError::getDefaultMessage)
            .toList());
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ProblemDetail> handleHttpMessageNotReadableException(
        HttpMessageNotReadableException e, Locale locale) {
        String localizedMessage = messageSource.getMessage("errors.error.400.not_readable_request",
            null, "errors.error.400.default", locale);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
            localizedMessage);
        problemDetail.setProperty("error", e.getMessage());
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleEntityNotFoundException(EntityNotFoundException e,
                                                                       Locale locale) {
        String localizedErrorMessage = messageSource.getMessage("errors.error.404.detail",
            null, "errors.error.404.default", locale);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
            localizedErrorMessage);
        problemDetail.setProperty("entity", e.getEntityName());
        problemDetail.setProperty("ids", e.getEntityIds());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(problemDetail);
    }
}
