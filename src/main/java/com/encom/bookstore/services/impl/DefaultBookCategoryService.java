package com.encom.bookstore.services.impl;

import com.encom.bookstore.dto.BookCategoryCreateDto;
import com.encom.bookstore.dto.BookCategoryDto;
import com.encom.bookstore.exceptions.EntityNotFoundException;
import com.encom.bookstore.mappers.BookCategoryMapper;
import com.encom.bookstore.model.BookCategory;
import com.encom.bookstore.repositories.BookCategoryRepository;
import com.encom.bookstore.services.BookCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @Transactional
    public BookCategoryDto createBookCategory(BookCategoryCreateDto bookCategoryCreateDto) {
        BookCategory bookCategory = bookCategoryMapper.bookCategoryCreateDtoToBookCategory(bookCategoryCreateDto);
        bookCategoryRepository.save(bookCategory);
        return bookCategoryMapper.bookCategoryToBookCategoryDto(bookCategory);
    }

    @Override
    public BookCategoryDto findBookCategory(long categoryId) {
        BookCategory bookCategory = getBookCategory(categoryId);
        return bookCategoryMapper.bookCategoryToBookCategoryDto(bookCategory);
    }

    @Override
    public Page<BookCategoryDto> findAllBookCategories(Pageable pageable) {
        Page<BookCategory> bookCategoriesPage = bookCategoryRepository.findAll(pageable);
        return bookCategoriesPage.map(bookCategoryMapper::bookCategoryToBookCategoryDto);
    }

    @Override
    public Page<BookCategoryDto> findAllBookCategoriesByKeyword(Pageable pageable, String keyword) {
        Page<BookCategory> bookCategoriesPage = bookCategoryRepository.findAllByKeyword(pageable, keyword);
        return bookCategoriesPage.map(bookCategoryMapper::bookCategoryToBookCategoryDto);
    }

    @Override
    public BookCategory getBookCategory(long categoryId) {
        return bookCategoryRepository.findById(categoryId)
            .orElseThrow(() -> new EntityNotFoundException("BookCategory",
                Set.of(categoryId)));
    }
}
