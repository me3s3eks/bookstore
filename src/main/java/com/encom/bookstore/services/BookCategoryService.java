package com.encom.bookstore.services;

import com.encom.bookstore.dto.BookCategoryDto;
import com.encom.bookstore.model.BookCategory;

public interface BookCategoryService {

    BookCategoryDto findBookCategory(long categoryId);

    BookCategory getBookCategory(long categoryId);
}
