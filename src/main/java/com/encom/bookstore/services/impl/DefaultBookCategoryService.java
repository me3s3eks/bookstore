package com.encom.bookstore.services.impl;

import com.encom.bookstore.dto.BookCategoryDto;
import com.encom.bookstore.exceptions.EntityNotFoundException;
import com.encom.bookstore.mappers.BookCategoryMapper;
import com.encom.bookstore.model.BookCategory;
import com.encom.bookstore.repositories.BookCategoryRepository;
import com.encom.bookstore.services.BookCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultBookCategoryService implements BookCategoryService {

    private final BookCategoryRepository bookCategoryRepository;

    private final BookCategoryMapper bookCategoryMapper;

    @Override
    public BookCategoryDto findBookCategory(long categoryId) {
        BookCategory bookCategory = getBookCategory(categoryId);
        return bookCategoryMapper.bookCategoryToBookCategoryDto(bookCategory);
    }

    @Override
    public BookCategory getBookCategory(long categoryId) {
        return bookCategoryRepository.findById(categoryId)
            .orElseThrow(() -> new EntityNotFoundException("BookCategory",
                Set.of(categoryId)));
    }
}
