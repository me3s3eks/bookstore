package com.encom.bookstore.exceptions;

import lombok.Getter;

@Getter
public class InvalidRequestDataException extends RuntimeException {

    private final String fieldName;

    public InvalidRequestDataException(String fieldName, String message) {
        super(message);
        this.fieldName = fieldName;
    }
}
