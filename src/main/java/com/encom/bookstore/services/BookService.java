package com.encom.bookstore.services;

import com.encom.bookstore.dto.BookBaseInfoDto;
import com.encom.bookstore.dto.BookDto;
import com.encom.bookstore.dto.BookFilterDto;
import com.encom.bookstore.dto.BookRequestDto;
import com.encom.bookstore.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    BookDto createBook(BookRequestDto bookRequestDto);

    BookDto findBook(long bookId);

    Page<BookBaseInfoDto> findBooksByFilterDto(Pageable pageable, BookFilterDto bookFilterDto);

    Book getBook(long bookId);

    void deleteBook(long bookId);

    void restoreBook(long bookId);

    void updateBook(long bookId, BookRequestDto bookRequestDto);
}


