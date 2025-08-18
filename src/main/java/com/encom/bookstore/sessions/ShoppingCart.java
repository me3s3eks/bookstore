package com.encom.bookstore.sessions;

import com.encom.bookstore.model.CartItem;

import java.util.Optional;
import java.util.Set;

public interface ShoppingCart {

    boolean addItem(CartItem item);

    boolean isEmpty();

    boolean containsItem(CartItem item);

    boolean updateItem(CartItem item);

    boolean removeItem(CartItem cartItem);

    Set<CartItem> getCartItems();

    void clear();
}
