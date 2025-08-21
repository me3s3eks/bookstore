package com.encom.bookstore.exceptions;

import com.encom.bookstore.model.ReceivingMethod;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class UnavailablePaymentMethodException extends RuntimeException {

    private final ReceivingMethod receivingMethod;

    public UnavailablePaymentMethodException(ReceivingMethod receivingMethod, String message) {
        super(message);
        this.receivingMethod = receivingMethod;
    }
}
