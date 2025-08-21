package com.encom.bookstore.services;

import com.encom.bookstore.model.Order;
import com.encom.bookstore.model.OrderedBook;
import com.encom.bookstore.sessions.ShoppingCart;

import java.util.List;

public interface OrderedBookService {

    void addBooksToOrder(Order order, ShoppingCart shoppingCart);

    List<OrderedBook> getOrderedBooksForOrder(Order order);
}
