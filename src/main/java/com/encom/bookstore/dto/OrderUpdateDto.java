package com.encom.bookstore.dto;

import com.encom.bookstore.model.OrderStatus;
import com.encom.bookstore.model.PaymentStatus;

public record OrderUpdateDto(

    PaymentStatus paymentStatus,

    OrderStatus orderStatus,

    Boolean cancelled) {
}
