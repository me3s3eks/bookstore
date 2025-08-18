package com.encom.bookstore.dto;

import java.util.List;

public record ShoppingCartResponseDto (

  List<CartItemResponseDto> cartItems) {
}
