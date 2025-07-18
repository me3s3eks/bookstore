package com.encom.bookstore.controllers;

import com.encom.bookstore.dto.BookBaseInfoDto;
import com.encom.bookstore.dto.BookDto;
import com.encom.bookstore.dto.BookFilterDto;
import com.encom.bookstore.dto.BookRequestDto;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/catalogue")
public class BooksRestController {

    private final BookService bookService;

    @PostMapping("/books")
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody BookRequestDto bookRequestDto,
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
            BookDto bookDto = bookService.createBook(bookRequestDto);
            return ResponseEntity
                .created(uriBuilder
                    .replacePath("/catalogue/books/{bookId}")
                    .build(bookDto.id()))
                .body(bookDto);
        }
    }

    @GetMapping("/books")
    public ResponseEntity<Page<BookBaseInfoDto>> getBooks(Pageable pageable,
                                                          @Valid @RequestBody(required = false) BookFilterDto bookFilterDto,
                                                          BindingResult bindingResult)
    throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException bindException) {
                throw bindException;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            Page<BookBaseInfoDto> bookPage = bookService.findBooksByFilterDto(pageable, bookFilterDto);
            return ResponseEntity.ok(bookPage);
        }
    }

    //~exclude negative id pattern (delete dash)
    @GetMapping("/books/{bookId:[-\\d]+}")
    public ResponseEntity<BookDto> getBook(@PathVariable long bookId) {
        BookDto bookDto = bookService.findBook(bookId);
        return ResponseEntity.ok(bookDto);
    }

    @GetMapping("/authors/{authorId}/books")
    public ResponseEntity<Page<BookBaseInfoDto>> getBooksForAuthor(@PathVariable long authorId,
                                                                   Pageable pageable) {
        Page<BookBaseInfoDto> bookPage = bookService.findBooksByAuthor(authorId, pageable);
        return ResponseEntity.ok(bookPage);
    }

    //~exclude negative id pattern (delete dash)
    @PutMapping("/books/{bookId:[-\\d]+}")
    public ResponseEntity<Void> updateBook(@PathVariable long bookId,
                                           @Valid @RequestBody BookRequestDto bookRequestDto,
                                           BindingResult bindingResult)
    throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException bindException) {
                throw bindException;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            bookService.updateBook(bookId, bookRequestDto);
            return ResponseEntity.noContent().build();
        }
    }

    //~exclude negative id pattern (delete dash)
    @DeleteMapping("/books/{bookId:[-\\d]+}")
    public ResponseEntity<Void> deleteBook(@PathVariable long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }

    //~exclude negative id pattern (delete dash)
    @PostMapping("/books/{bookId:[-\\d]+}/restore")
    public ResponseEntity<Void> restoreBook(@PathVariable long bookId) {
        bookService.restoreBook(bookId);
        return ResponseEntity.noContent().build();
    }
}
