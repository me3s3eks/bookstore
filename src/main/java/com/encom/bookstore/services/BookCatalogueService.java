package com.encom.bookstore.services;

import com.encom.bookstore.dto.BookBaseInfoDto;
import com.encom.bookstore.dto.BookCreateDto;
import com.encom.bookstore.dto.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookCatalogueService {
    Page<BookBaseInfoDto> findAllBooks(Pageable pageable);

    BookDto createBook(BookCreateDto bookCreateDto);
}
