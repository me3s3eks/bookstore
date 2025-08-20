package com.encom.bookstore.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DeliveryAddressAlreadyExistsException extends RuntimeException {

    public DeliveryAddressAlreadyExistsException(String message) {
        super(message);
    }
}
