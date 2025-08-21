package com.encom.bookstore.specifications;

import com.encom.bookstore.model.DeliveryAddress;
import com.encom.bookstore.model.DeliveryAddress_;
import com.encom.bookstore.model.Order;
import com.encom.bookstore.model.OrderStatus;
import com.encom.bookstore.model.Order_;
import com.encom.bookstore.model.PaymentMethod;
import com.encom.bookstore.model.PaymentStatus;
import com.encom.bookstore.model.ReceivingMethod;
import com.encom.bookstore.model.User;
import com.encom.bookstore.model.User_;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Set;

public class OrderSpecifications {

    public static Specification<Order> withUser(long userId) {
        return (root, query, cb) -> {
            Join<Order, User> joinUser = root.join(Order_.user);
            return cb.equal(joinUser.get(User_.id), userId);
        };
    }

    public static Specification<Order> withPostalCodeIn(Set<String> postalCodes) {
        if (postalCodes == null || postalCodes.isEmpty()) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) -> {
            Join<Order, DeliveryAddress> joinAddress = root.join(Order_.deliveryAddress);
            return joinAddress.get(DeliveryAddress_.postalCode).in(postalCodes);
        };
    }

    public static Specification<Order> byPaymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod == null) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) ->
            cb.equal(root.get(Order_.paymentMethod), paymentMethod);
    }

    public static Specification<Order> byReceivingMethod(ReceivingMethod receivingMethod) {
        if (receivingMethod == null) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) ->
            cb.equal(root.get(Order_.paymentMethod), receivingMethod);
    }

    public static Specification<Order> byPaymentStatus(PaymentStatus paymentStatus) {
        if (paymentStatus == null) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) ->
            cb.equal(root.get(Order_.paymentMethod), paymentStatus);
    }

    public static Specification<Order> byOrderStatus(OrderStatus orderStatus) {
        if (orderStatus == null) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) ->
            cb.equal(root.get(Order_.paymentMethod), orderStatus);
    }

    public static Specification<Order> timeOfStatusChangedOnOrAfter(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) ->
            cb.greaterThanOrEqualTo(root.get(Order_.timeOfStatusChanged), localDateTime);
    }

    public static Specification<Order> timeOfStatusChangedOnOrBefore(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) ->
            cb.lessThanOrEqualTo(root.get(Order_.timeOfStatusChanged), localDateTime);
    }

    public static Specification<Order> byExpress(Boolean express) {
        if (express == null) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) ->
            cb.equal(root.get(Order_.express), express);
    }

    public static Specification<Order> byCancelled(Boolean cancelled) {
        if (cancelled == null) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) ->
            cb.equal(root.get(Order_.cancelled), cancelled);
    }
}
