package com.encom.bookstore.controllers;

import com.encom.bookstore.dto.BookCategoryDto;
import com.encom.bookstore.services.BookCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalogue/book-categories")
@RequiredArgsConstructor
public class BookCategoriesRestController {

    private final BookCategoryService bookCategoryService;

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
}
