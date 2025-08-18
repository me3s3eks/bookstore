package com.encom.bookstore.mappers.impl;

import com.encom.bookstore.dto.CartItemResponseDto;
import com.encom.bookstore.dto.ShoppingCartResponseDto;
import com.encom.bookstore.mappers.CartItemMapper;
import com.encom.bookstore.mappers.ShoppingCartMapper;
import com.encom.bookstore.model.CartItem;
import com.encom.bookstore.services.BookService;
import com.encom.bookstore.sessions.ShoppingCart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DefaultShoppingCartMapper implements ShoppingCartMapper {

    private final BookService bookService;

    private final CartItemMapper cartItemMapper;

    @Override
    public ShoppingCartResponseDto toShoppingCartResponseDto(ShoppingCart shoppingCart) {
        if (shoppingCart == null || shoppingCart.isEmpty()) {
            return new ShoppingCartResponseDto(Collections.emptyList());
        }

        Set<CartItem> cartItems = shoppingCart.getCartItems();
        List<CartItemResponseDto> cartItemResponseDtoList = cartItems.stream()
            .map(cartItemMapper::toCartItemResponseDto)
            .collect(Collectors.toList());

        return new ShoppingCartResponseDto(cartItemResponseDtoList);
    }
}
