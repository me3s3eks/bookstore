package com.encom.bookstore.mappers.impl;

import com.encom.bookstore.dto.OrderedBookResponseDto;
import com.encom.bookstore.mappers.BookMapper;
import com.encom.bookstore.mappers.OrderedBookMapper;
import com.encom.bookstore.model.Book;
import com.encom.bookstore.model.BookVariant;
import com.encom.bookstore.model.OrderedBook;
import com.encom.bookstore.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DefaultOrderedBookMapper implements OrderedBookMapper {

    private final BookService bookService;

    private final BookMapper bookMapper;

    @Override
    public OrderedBookResponseDto toOrderedBookResponseDto(OrderedBook orderedBook) {
        BookVariant bookVariant = orderedBook.getBookVariant();
        Book book = bookService.getBook(bookVariant.getId().getBookId());
        BigDecimal totalPrice = orderedBook.getPrice().multiply(BigDecimal.valueOf(orderedBook.getQuantity()));
        totalPrice = totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);

        return OrderedBookResponseDto.builder()
            .orderId(orderedBook.getOrder().getId())
            .bookBaseInfoDto(bookMapper.bookToBookBaseInfoDto(book))
            .bookType(bookVariant.getId().getBookType())
            .quantity(orderedBook.getQuantity())
            .itemPrice(orderedBook.getPrice())
            .totalPrice(totalPrice)
            .build();
    }
}
