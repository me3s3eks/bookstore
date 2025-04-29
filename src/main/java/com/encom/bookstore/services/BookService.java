package com.encom.bookstore.services;

import com.encom.bookstore.dto.BookDto;
import com.encom.bookstore.dto.BookUpdateDto;
import com.encom.bookstore.model.Book;

public interface BookService {

    BookDto findBook(long bookId);

    Book getBook(long bookId);

    void deleteBook(long bookId);

    void updateBook(long bookId, BookUpdateDto bookUpdateDto);

    void restoreBook(long bookId);
}


