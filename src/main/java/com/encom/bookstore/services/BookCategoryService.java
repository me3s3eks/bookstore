package com.encom.bookstore.services;

import com.encom.bookstore.dto.BookCategoryCreateDto;
import com.encom.bookstore.dto.BookCategoryDto;
import com.encom.bookstore.model.BookCategory;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookCategoryService {

    BookCategoryDto createBookCategory(BookCategoryCreateDto bookCategoryCreateDto);

    BookCategoryDto findBookCategory(long categoryId);

    Page<BookCategoryDto> findAllBookCategories(Pageable pageable);

    Page<BookCategoryDto> findAllBookCategoriesByKeyword(Pageable pageable, String keyword);

    BookCategory getBookCategory(long categoryId);
}
