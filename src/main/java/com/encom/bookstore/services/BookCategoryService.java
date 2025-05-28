package com.encom.bookstore.services;

import com.encom.bookstore.dto.BookCategoryRequestDto;
import com.encom.bookstore.dto.BookCategoryDto;
import com.encom.bookstore.model.BookCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookCategoryService {

    BookCategoryDto createBookCategory(BookCategoryRequestDto bookCategoryRequestDto);

    BookCategoryDto findBookCategory(long categoryId);

    Page<BookCategoryDto> findAllBookCategories(Pageable pageable);

    Page<BookCategoryDto> findAllBookCategoriesByKeyword(Pageable pageable, String keyword);

    BookCategory getBookCategory(long categoryId);

    void deleteBookCategory(long categoryId);

    void updateBookCategory(long categoryId, BookCategoryRequestDto bookCategoryRequestDto);
}
