package com.encom.bookstore.services;

import com.encom.bookstore.dto.BookVariantDto;
import com.encom.bookstore.model.BookType;
import com.encom.bookstore.model.BookVariant;
import com.encom.bookstore.model.BookVariantId;
import com.encom.bookstore.model.CartItem;

import java.util.List;

public interface BookVariantService {

    BookVariantDto createBookVariant(BookVariantDto bookVariantDto);

    BookVariantDto findBookVariant(long bookId, BookType bookType);

    List<BookVariantDto> findBookVariants(long bookId);

    BookVariant getBookVariant(BookVariantId bookVariantId);

    void updateBookVariant(BookVariantDto bookVariantDto);

    BookVariant getBookVariantForUpdate(BookVariantId bookVariantId);

    void checkBookVariantAvailability(BookVariantId bookVariantId, CartItem cartItem);

    void updateBookVariantAfterAddToOrder(BookVariantId bookVariantId, CartItem cartItem);
}
