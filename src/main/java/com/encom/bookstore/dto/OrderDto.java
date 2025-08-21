package com.encom.bookstore.dto;

import com.encom.bookstore.model.OrderStatus;
import com.encom.bookstore.model.PaymentMethod;
import com.encom.bookstore.model.PaymentStatus;
import com.encom.bookstore.model.ReceivingMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class OrderDto {

    private long id;

    private long userId;

    private BigDecimal totalPrice;

    private long deliveryAddressId;

    private PaymentMethod paymentMethod;

    private ReceivingMethod receivingMethod;

    private PaymentStatus paymentStatus;

    private OrderStatus orderStatus;

    private LocalDateTime timeOfStatusChanged;

    private boolean express;

    private boolean cancelled;
}
