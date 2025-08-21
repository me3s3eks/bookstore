package com.encom.bookstore.repositories;

import com.encom.bookstore.model.Order;
import com.encom.bookstore.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @EntityGraph(attributePaths = {"user", "deliveryAddress"})
    Optional<Order> findWithAllRelatedEntitiesByIdAndUser(long orderId, User user);

    @EntityGraph(attributePaths = {"user", "deliveryAddress"})
    Optional<Order> findWithAllRelatedEntitiesById(long orderId);
}
