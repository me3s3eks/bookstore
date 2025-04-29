package com.encom.bookstore.services;

import com.encom.bookstore.dto.BookBaseInfoDto;
import com.encom.bookstore.dto.BookCreateDto;
import com.encom.bookstore.dto.BookDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookCatalogueService {

    BookDto createBook(BookCreateDto bookCreateDto);

    Page<BookBaseInfoDto> findAllBooks(Pageable pageable);
}
