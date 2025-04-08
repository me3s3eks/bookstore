package com.encom.bookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @SequenceGenerator(
            name = "order_seq",
            sequenceName = "orders_sequence",
            allocationSize = 30
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_address_id")
    @ToString.Exclude
    private DeliveryAddress deliveryAddress;

    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Column(name = "receiving_method")
    private ReceivingMethod receivingMethod;

    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "status_changed_at", nullable = false)
    private LocalDateTime timeOfStatusChanged;

    @Column(name = "is_express", nullable = false)
    private boolean express;

    @Column(nullable = false)
    private boolean cancelled;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order other = (Order) o;
        if (!other.canEqual(this)) return false;
        return id != null && id.equals(other.getId());
    }

    protected boolean canEqual(Object other) {
        return other instanceof Order;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
