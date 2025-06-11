package com.encom.bookstore.services.impl;

import com.encom.bookstore.dto.BookCategoryDto;
import com.encom.bookstore.dto.BookCategoryRequestDto;
import com.encom.bookstore.exceptions.EntityNotFoundException;
import com.encom.bookstore.exceptions.ForeignKeyDeleteConstraintException;
import com.encom.bookstore.mappers.BookCategoryMapper;
import com.encom.bookstore.model.BookCategory;
import com.encom.bookstore.repositories.BookCategoryRepository;
import com.encom.bookstore.repositories.BookRepository;
import com.encom.bookstore.services.BookCategoryService;
import com.encom.bookstore.specifications.BookCategorySpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultBookCategoryService implements BookCategoryService {

    private final BookCategoryRepository bookCategoryRepository;

    private final BookRepository bookRepository;

    private final BookCategoryMapper bookCategoryMapper;

    @Lazy
    private final DefaultBookCategoryService bookCategoryService;

    @Override
    @Transactional
    public BookCategoryDto createBookCategory(BookCategoryRequestDto bookCategoryRequestDto) {
        BookCategory bookCategory = bookCategoryMapper.bookCategoryRequestDtoToBookCategory(bookCategoryRequestDto);
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
        if (keyword == null) {
            return findAllBookCategories(pageable);
        } else {
            Specification<BookCategory> bookCategorySpecification = getBookCategorySpecificationForKeyword(keyword);
            Page<BookCategory> bookCategoriesPage = bookCategoryRepository.findAll(bookCategorySpecification, pageable);
            return bookCategoriesPage.map(bookCategoryMapper::bookCategoryToBookCategoryDto);
        }
    }

    @Override
    public Set<Long> findBookCategoryIdsInSubtree(long rootId) {
        return bookCategoryRepository.findIdsInSubtree(rootId).stream()
            .collect(Collectors.toSet());
    }

    @Override
    public Set<Long> findBookCategoryIdsInSubtree(Set<Long> rootIds) {
        return rootIds.stream()
            .map(bookCategoryService::findBookCategoryIdsInSubtree)
            .flatMap(Set::stream)
            .collect(Collectors.toSet());
    }

    @Override
    public BookCategory getBookCategory(long categoryId) {
        return bookCategoryRepository.findById(categoryId)
            .orElseThrow(() -> new EntityNotFoundException("BookCategory",
                Set.of(categoryId)));
    }

    @Override
    @Transactional
    public void deleteBookCategory(long categoryId) {
        BookCategory bookCategory = getBookCategory(categoryId);
        if (bookCategoryRepository.existsByParentId(categoryId)) {
            throw new ForeignKeyDeleteConstraintException("BookCategory");
        }
        if (bookRepository.existsByBookCategoryId(categoryId)) {
            throw new ForeignKeyDeleteConstraintException("Book");
        }
        bookCategoryRepository.delete(bookCategory);
    }

    @Override
    @Transactional
    public void updateBookCategory(long categoryId, BookCategoryRequestDto bookCategoryRequestDto) {
        BookCategory bookCategory = getBookCategory(categoryId);
        bookCategoryMapper.updateBookCategoryFromDto(bookCategoryRequestDto, bookCategory);
        bookCategoryRepository.save(bookCategory);
    }

    private Specification<BookCategory> getBookCategorySpecificationForKeyword(String keyword) {
        return BookCategorySpecifications.nameLike(keyword);
    }
}
