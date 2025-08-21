package com.encom.bookstore.repositories;

import com.encom.bookstore.model.Order;
import com.encom.bookstore.model.OrderedBook;
import com.encom.bookstore.model.OrderedBookId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderedBookRepository extends JpaRepository<OrderedBook, OrderedBookId> {

    List<OrderedBook> getOrderedBookByOrder(Order order);
}
