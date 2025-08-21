package com.encom.bookstore.services;

import com.encom.bookstore.dto.CartItemRequestDto;
import com.encom.bookstore.dto.CartItemResponseDto;
import com.encom.bookstore.dto.ShoppingCartResponseDto;
import com.encom.bookstore.dto.ShoppingCartTotalPriceDto;
import com.encom.bookstore.dto.ShoppingCartTotalTypesDto;
import com.encom.bookstore.sessions.ShoppingCart;

public interface ShoppingCartService {

    ShoppingCartResponseDto getShoppingCart();

    ShoppingCartTotalTypesDto getTotalDistinctItems();

    ShoppingCartTotalPriceDto getTotalPrice();

    CartItemResponseDto addItemToCart(CartItemRequestDto cartItemRequestDto);

    CartItemResponseDto updateCartItem(CartItemRequestDto cartItemRequestDto);

    void clearCart();

    void removeCartItem(CartItemRequestDto cartItemRequestDto);

    ShoppingCart retrieveShoppingCart();
}
