package com.encom.bookstore.repositories;

import com.encom.bookstore.model.DeliveryAddress;
import com.encom.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long>,
    JpaSpecificationExecutor<DeliveryAddress> {

    List<DeliveryAddress> findAllByUserAndTimeOfRemovalIsNull(User user);

    Optional<DeliveryAddress> findByIdAndUser(long addressId, User currentUser);
}
