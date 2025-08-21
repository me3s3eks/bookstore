package com.encom.bookstore.dto;

import com.encom.bookstore.model.OrderStatus;
import com.encom.bookstore.model.PaymentMethod;
import com.encom.bookstore.model.PaymentStatus;
import com.encom.bookstore.model.ReceivingMethod;

public record OrderResponseDto(

    long id,

    PaymentMethod paymentMethod,

    ReceivingMethod receivingMethod,

    PaymentStatus paymentStatus,

    OrderStatus orderStatus,

    boolean express) {
}
