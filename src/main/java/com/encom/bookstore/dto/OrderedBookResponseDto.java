package com.encom.bookstore.dto;

import com.encom.bookstore.model.BookType;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class OrderedBookResponseDto {

    private final long orderId;

    private final BookBaseInfoDto bookBaseInfoDto;

    private final BookType bookType;

    private final int quantity;

    private final BigDecimal itemPrice;

    private final BigDecimal totalPrice;
}
