package com.encom.bookstore.dto;

import com.encom.bookstore.model.OrderStatus;
import com.encom.bookstore.model.PaymentMethod;
import com.encom.bookstore.model.PaymentStatus;
import com.encom.bookstore.model.ReceivingMethod;

import java.time.LocalDateTime;
import java.util.Set;

public record OrderFilterDto(

    Long userId,

    Set<String> postalCodes,

    PaymentMethod paymentMethod,

    ReceivingMethod receivingMethod,

    PaymentStatus paymentStatus,

    OrderStatus orderStatus,

    LocalDateTime timeOfStatusChangedBefore,

    LocalDateTime timeOfStatusChangedAfter,

    Boolean express,

    Boolean cancelled) {
}
