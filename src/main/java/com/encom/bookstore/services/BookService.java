package com.encom.bookstore.services;

import com.encom.bookstore.dto.BookBaseInfoDto;
import com.encom.bookstore.dto.BookCreateDto;
import com.encom.bookstore.dto.BookDto;
import com.encom.bookstore.dto.BookUpdateDto;
import com.encom.bookstore.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    BookDto createBook(BookCreateDto bookCreateDto);

    BookDto findBook(long bookId);

    Page<BookBaseInfoDto> findAllBooks(Pageable pageable);

    Book getBook(long bookId);

    void deleteBook(long bookId);

    void updateBook(long bookId, BookUpdateDto bookUpdateDto);

    void restoreBook(long bookId);
}


