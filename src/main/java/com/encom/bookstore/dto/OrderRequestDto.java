package com.encom.bookstore.dto;

import com.encom.bookstore.model.PaymentMethod;
import com.encom.bookstore.model.ReceivingMethod;

public record OrderRequestDto(

    long deliveryAddressId,

    PaymentMethod paymentMethod,

    ReceivingMethod receivingMethod,

    boolean express) {
}
