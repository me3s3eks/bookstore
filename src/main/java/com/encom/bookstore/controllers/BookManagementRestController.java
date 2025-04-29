package com.encom.bookstore.controllers;

import com.encom.bookstore.dto.BookDto;
import com.encom.bookstore.dto.BookUpdateDto;
import com.encom.bookstore.services.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequiredArgsConstructor
//~exclude negative id pattern (delete dash)
@RequestMapping("/catalogue/books/{bookId:[-\\d]+}")
public class BookManagementRestController {

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<BookDto> getBook(@PathVariable long bookId) {
        BookDto bookDto = bookService.findBook(bookId);
        return ResponseEntity.ok(bookDto);
    }

    @PatchMapping
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

    @PostMapping("/restore")
    public ResponseEntity<Void> restoreBook(@PathVariable long bookId) {
        bookService.restoreBook(bookId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBook(@PathVariable long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }

}
