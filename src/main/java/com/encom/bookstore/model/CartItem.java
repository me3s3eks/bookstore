package com.encom.bookstore.model;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Objects;

@Value
@Builder
public class CartItem {

    private final long bookId;

    private final BookType bookType;

    private final int quantity;

    private final BigDecimal itemPrice;

    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = itemPrice.multiply(BigDecimal.valueOf(quantity));
        return totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem item = (CartItem) o;
        return bookId == item.bookId
            && bookType == item.bookType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, bookType);
    }
}
