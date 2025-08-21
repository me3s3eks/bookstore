package com.encom.bookstore.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EmptyShoppingCartException extends RuntimeException {

    public EmptyShoppingCartException(String message) {
        super(message);
    }
}
