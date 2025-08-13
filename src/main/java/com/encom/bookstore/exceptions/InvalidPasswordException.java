package com.encom.bookstore.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException(String message) {
        super(message);
    }
}
