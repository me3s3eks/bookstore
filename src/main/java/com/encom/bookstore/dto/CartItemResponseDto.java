package com.encom.bookstore.dto;

import com.encom.bookstore.model.BookType;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class CartItemResponseDto {

    private final long bookId;

    private final BookType bookType;

    private final String bookTitle;

    private final int bookEdition;

    private final String isbn;

    private final int quantity;

    private final BigDecimal itemPrice;

    private final BigDecimal totalPrice;
}
