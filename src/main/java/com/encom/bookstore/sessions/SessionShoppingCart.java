package com.encom.bookstore.sessions;

import com.encom.bookstore.model.CartItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
@SessionScope
@NoArgsConstructor
@Getter
public class SessionShoppingCart implements ShoppingCart {

    private final Set<CartItem> cartItems = new LinkedHashSet<>();

    @Override
    public boolean addItem(CartItem item) {
        return cartItems.add(item);
    }

    @Override
    public boolean isEmpty() {
        return cartItems.isEmpty();
    }

    @Override
    public boolean containsItem(CartItem item) {
        return cartItems.contains(item);
    }

    @Override
    public boolean updateItem(CartItem item) {
        if (cartItems.contains(item)) {
            cartItems.remove(item);
        }
        return cartItems.add(item);
    }

    @Override
    public boolean removeItem(CartItem cartItem) {
        return cartItems.remove(cartItem);
    }

    @Override
    public void clear() {
        cartItems.clear();
    }
}
