package com.encom.bookstore.mappers;

import com.encom.bookstore.dto.ShoppingCartResponseDto;
import com.encom.bookstore.sessions.ShoppingCart;

public interface ShoppingCartMapper {

    ShoppingCartResponseDto toShoppingCartResponseDto(ShoppingCart shoppingCart);
}
