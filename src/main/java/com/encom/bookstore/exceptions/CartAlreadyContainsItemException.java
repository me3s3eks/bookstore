package com.encom.bookstore.exceptions;

import com.encom.bookstore.model.BookType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CartAlreadyContainsItemException extends RuntimeException {

    private final long bookId;

    private final BookType bookType;
}
