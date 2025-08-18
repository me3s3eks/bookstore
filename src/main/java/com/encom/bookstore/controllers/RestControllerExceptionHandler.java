package com.encom.bookstore.controllers;

import com.encom.bookstore.exceptions.BookVariantNotAvailableException;
import com.encom.bookstore.exceptions.CartAlreadyContainsItemException;
import com.encom.bookstore.exceptions.CartNotContainsItemException;
import com.encom.bookstore.exceptions.EntityAlreadyExistsException;
import com.encom.bookstore.exceptions.EntityNotFoundException;
import com.encom.bookstore.exceptions.ForeignKeyDeleteConstraintException;
import com.encom.bookstore.exceptions.InvalidRequestDataException;
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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, localizedMessage);
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
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, localizedMessage);
        problemDetail.setProperty("error", e.getMessage());
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleEntityNotFoundException(EntityNotFoundException e,
                                                                       Locale locale) {
        String localizedErrorMessage = messageSource.getMessage("errors.error.404.detail",
            null, "errors.error.404.default", locale);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, localizedErrorMessage);
        problemDetail.setProperty("entityName", e.getEntityName());
        problemDetail.setProperty("entityIds", e.getEntityIds());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(ForeignKeyDeleteConstraintException.class)
    public ResponseEntity<ProblemDetail> handleForeignKeyDeleteConstraintException(
        ForeignKeyDeleteConstraintException e,
        Locale locale) {
        String localizedMessage = messageSource.getMessage("errors.error.400." +
                "foreign_key_delete_constraint_violation",
            null, "errors.error.400.default", locale);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, localizedMessage);
        problemDetail.setProperty("dependentEntityName", e.getDependentEntityName());
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ProblemDetail> handleMethodArgumentTypeMismatchException(
        MethodArgumentTypeMismatchException e,
        Locale locale) {
        String localizedMessage = messageSource.getMessage("errors.error.400." +
            "method_argument_type_mismatch",
            new Object[]{e.getName(), e.getRequiredType()}, "errors.error.400.default", locale);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, localizedMessage);
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(InvalidRequestDataException.class)
    public ResponseEntity<ProblemDetail> handleInvalidRequestDataException(InvalidRequestDataException e,
                                                                           Locale locale) {
        String localizedMessage = messageSource.getMessage("errors.error.400.invalid_request_data",
            new Object[]{e.getFieldName(), e.getMessage()}, "errors.error.400.default", locale);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, localizedMessage);
        return ResponseEntity.badRequest().body(problemDetail);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleEntityAlreadyExistsException(EntityAlreadyExistsException e,
                                                                            Locale locale) {
        String localizedMessage = messageSource.getMessage("errors.error.409.entity_already_exists",
            null, "errors.error.409.default", locale);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, localizedMessage);
        problemDetail.setProperty("entityId", e.getEntityId());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }

    @ExceptionHandler(CartAlreadyContainsItemException.class)
    public ResponseEntity<ProblemDetail> handleCartAlreadyContainsItemException(CartAlreadyContainsItemException e,
                                                                                Locale locale) {
        String localizedMessage = messageSource.getMessage("errors.error.409.cart_already_contains_item",
            null, "errors.error.409.default", locale);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, localizedMessage);
        problemDetail.setProperty("bookId", e.getBookId());
        problemDetail.setProperty("bookType", e.getBookType());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }

    @ExceptionHandler(CartNotContainsItemException.class)
    public ResponseEntity<ProblemDetail> handleCartNotContainsItemException(CartNotContainsItemException e,
                                                                            Locale locale) {
        String localizedMessage = messageSource.getMessage("errors.error.404.cart_not_contains_item",
            null, "errors.error.409.default", locale);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, localizedMessage);
        problemDetail.setProperty("bookId", e.getBookId());
        problemDetail.setProperty("bookType", e.getBookType());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(BookVariantNotAvailableException.class)
    public ResponseEntity<ProblemDetail> handleBookVariantNotAvailableException(BookVariantNotAvailableException e,
                                                                                Locale locale) {
        String localizedMessage = messageSource.getMessage("errors.error.400.book_variant_not_available",
            null, "errors.error.409.default", locale);
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, localizedMessage);
        problemDetail.setProperty("bookId", e.getBookId());
        problemDetail.setProperty("bookType", e.getBookType());
        problemDetail.setProperty("availabilityStatus", e.getAvailabilityStatus());
        if (e.getAvailableQuantity() != null) {
            problemDetail.setProperty("availableQuantity", e.getAvailableQuantity());
        }
        return ResponseEntity.badRequest().body(problemDetail);
    }
}
