package com.encom.bookstore.dto;

import com.encom.bookstore.model.BookType;

public record CartItemRequestDto(

    long bookId,

    BookType bookType,

    int quantity) {
}
