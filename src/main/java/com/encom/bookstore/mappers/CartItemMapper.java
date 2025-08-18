package com.encom.bookstore.mappers;

import com.encom.bookstore.dto.CartItemRequestDto;
import com.encom.bookstore.dto.CartItemResponseDto;
import com.encom.bookstore.model.CartItem;

public interface CartItemMapper {

    CartItemResponseDto toCartItemResponseDto(CartItem cartItem);

    CartItem toCartItem(CartItemRequestDto cartItemRequestDto);
}
