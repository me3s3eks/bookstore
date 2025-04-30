package com.encom.bookstore.controllers;

import com.encom.bookstore.dto.BookBaseInfoDto;
import com.encom.bookstore.dto.BookCreateDto;
import com.encom.bookstore.dto.BookDto;
import com.encom.bookstore.dto.BookUpdateDto;
import com.encom.bookstore.services.BookService;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/management/catalogue/books/")
public class BooksManagementRestController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<Page<BookBaseInfoDto>> getAllBooks(Pageable pageable) {
        Page<BookBaseInfoDto> bookPage = bookService.findAllBooks(pageable);
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
            BookDto bookDto = bookService.createBook(bookCreateDto);
            return ResponseEntity
                .created(uriBuilder
                    .replacePath("/management/catalogue/books/{bookId}")
                    .build(bookDto.id()))
                .body(bookDto);
        }
    }

    //~exclude negative id pattern (delete dash)
    @GetMapping("/{bookId:[-\\d]+}")
    public ResponseEntity<BookDto> getBook(@PathVariable long bookId) {
        BookDto bookDto = bookService.findBook(bookId);
        return ResponseEntity.ok(bookDto);
    }

    //~exclude negative id pattern (delete dash)
    @PatchMapping("/{bookId:[-\\d]+}")
    public ResponseEntity<Void> updateBook(@PathVariable long bookId,
                                           @Valid @RequestBody BookUpdateDto bookUpdateDto,
                                           BindingResult bindingResult)
    throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException bindException) {
                throw bindException;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            bookService.updateBook(bookId, bookUpdateDto);
            return ResponseEntity.noContent().build();
        }
    }

    //~exclude negative id pattern (delete dash)
    @PostMapping("/{bookId:[-\\d]+}/restore")
    public ResponseEntity<Void> restoreBook(@PathVariable long bookId) {
        bookService.restoreBook(bookId);
        return ResponseEntity.noContent().build();
    }

    //~exclude negative id pattern (delete dash)
    @DeleteMapping("/{bookId:[-\\d]+}")
    public ResponseEntity<Void> deleteBook(@PathVariable long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }

}
