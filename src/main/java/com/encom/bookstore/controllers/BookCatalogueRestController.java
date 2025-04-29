package com.encom.bookstore.controllers;

import com.encom.bookstore.dto.BookBaseInfoDto;
import com.encom.bookstore.dto.BookCreateDto;
import com.encom.bookstore.dto.BookDto;
import com.encom.bookstore.services.BookCatalogueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/catalogue/books")
@RequiredArgsConstructor
public class BookCatalogueRestController {

    private final BookCatalogueService bookCatalogueService;

    @GetMapping
    public ResponseEntity<Page<BookBaseInfoDto>> getAllBooks(Pageable pageable) {
        Page<BookBaseInfoDto> bookPage = bookCatalogueService.findAllBooks(pageable);
        return ResponseEntity.ok(bookPage);
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookCreateDto bookCreateDto,
                                              BindingResult bindingResult,
                                              UriComponentsBuilder uriBuilder)
    throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException bindException) {
                throw bindException;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            BookDto bookDto = bookCatalogueService.createBook(bookCreateDto);
            return ResponseEntity
                .created(uriBuilder
                    .replacePath("/catalogue/books/{bookId}")
                    .build(bookDto.id()))
                .body(bookDto);
        }
    }
}
