package com.encom.bookstore.controllers;

import com.encom.bookstore.dto.BookCategoryCreateDto;
import com.encom.bookstore.dto.BookCategoryDto;
import com.encom.bookstore.dto.BookCategoryUpdateDto;
import com.encom.bookstore.mappers.BookCategoryMapper;
import com.encom.bookstore.services.BookCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/catalogue/book-categories")
@RequiredArgsConstructor
public class BookCategoriesRestController {

    private final BookCategoryService bookCategoryService;
    private final BookCategoryMapper bookCategoryMapper;

    @PostMapping
    public ResponseEntity<BookCategoryDto> createBookCategory(
        @Valid @RequestBody BookCategoryCreateDto bookCategoryCreateDto,
        BindingResult bindingResult,
        UriComponentsBuilder uriComponentsBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException bindException) {
                throw bindException;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            BookCategoryDto bookCategoryDto = bookCategoryService.createBookCategory(bookCategoryCreateDto);
            return ResponseEntity
                .created(uriComponentsBuilder
                    .replacePath("/catalogue/book-categories/{categoryId}")
                    .build(bookCategoryDto.id()))
                .body(bookCategoryDto);
        }
    }

    @GetMapping
    public ResponseEntity<Page<BookCategoryDto>> getAllBookCategories(
        @RequestParam(name = "keyword", required = false) String keyword,
        Pageable pageable) {
        Page<BookCategoryDto> bookCategoryPage = null;
        if (keyword == null) {
            bookCategoryPage = bookCategoryService.findAllBookCategories(pageable);
        } else {
            bookCategoryPage = bookCategoryService.findAllBookCategoriesByKeyword(pageable, keyword);
        }
        return ResponseEntity.ok(bookCategoryPage);
    }

    @GetMapping("/{categoryId:\\d+}")
    public ResponseEntity<BookCategoryDto> getBookCategory(@PathVariable long categoryId) {
        BookCategoryDto bookCategoryDto = bookCategoryService.findBookCategory(categoryId);
        return ResponseEntity.ok(bookCategoryDto);
    }

    @PatchMapping("/{categoryId:\\d+}")
    public ResponseEntity<Void> updateBookCategory(@PathVariable long categoryId,
                                                   @Valid @RequestBody BookCategoryUpdateDto bookCategoryUpdateDto,
                                                   BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException bindException) {
                throw bindException;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
               bookCategoryService.updateBookCategory(categoryId, bookCategoryUpdateDto);
               return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{categoryId:\\d+}")
    public ResponseEntity<Void> deleteBookCategory(@PathVariable long categoryId) {
        bookCategoryService.deleteBookCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
}
