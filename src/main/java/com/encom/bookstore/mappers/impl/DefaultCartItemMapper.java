package com.encom.bookstore.mappers.impl;

import com.encom.bookstore.dto.CartItemRequestDto;
import com.encom.bookstore.dto.CartItemResponseDto;
import com.encom.bookstore.mappers.CartItemMapper;
import com.encom.bookstore.model.Book;
import com.encom.bookstore.model.BookVariant;
import com.encom.bookstore.model.BookVariantId;
import com.encom.bookstore.model.CartItem;
import com.encom.bookstore.services.BookService;
import com.encom.bookstore.services.BookVariantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DefaultCartItemMapper implements CartItemMapper {

    private final BookService bookService;

    private final BookVariantService bookVariantService;

    @Override
    public CartItemResponseDto toCartItemResponseDto(CartItem cartItem) {
        Book book = bookService.getBook(cartItem.getBookId());
        BigDecimal totalPrice = cartItem.getTotalPrice();

        return CartItemResponseDto.builder()
            .bookId(cartItem.getBookId())
            .bookType(cartItem.getBookType())
            .bookTitle(book.getTitle())
            .bookEdition(book.getEdition())
            .isbn(book.getIsbn())
            .quantity(cartItem.getQuantity())
            .itemPrice(cartItem.getItemPrice())
            .totalPrice(totalPrice)
            .build();
    }

    @Override
    public CartItem toCartItem(CartItemRequestDto cartItemRequestDto) {
        BookVariant bookVariant = bookVariantService.getBookVariant(
            new BookVariantId(cartItemRequestDto.bookId(), cartItemRequestDto.bookType()));
        return CartItem.builder()
            .bookId(cartItemRequestDto.bookId())
            .bookType(cartItemRequestDto.bookType())
            .quantity(cartItemRequestDto.quantity())
            .itemPrice(bookVariant.getPrice())
            .build();
    }
}
