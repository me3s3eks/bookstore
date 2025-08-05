package com.encom.bookstore.controllers;

import com.encom.bookstore.dto.BookVariantDto;
import com.encom.bookstore.model.BookType;
import com.encom.bookstore.services.BookVariantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
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
//~exclude negative id pattern (delete dash)
@RequestMapping("/catalogue/books/{bookId:[-\\d]+}/variants")
public class BookVariantsRestController {

    private final BookVariantService bookVariantService;

    @PostMapping
    public ResponseEntity<BookVariantDto> createBookVariant(
        @PathVariable long bookId,
        @Valid @RequestBody BookVariantDto bookVariantRequestDto,
        BindingResult bindingResult,
        UriComponentsBuilder uriBuilder) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException bindException) {
                throw bindException;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            bookVariantRequestDto.setBookId(bookId);
            BookVariantDto bookVariantResponseDto = bookVariantService.createBookVariant(bookVariantRequestDto);
            return ResponseEntity
                .created(uriBuilder
                    .replacePath("/catalogue/books/{bookId}/variants/{bookType}")
                    .build(bookVariantRequestDto.getBookId(), bookVariantRequestDto.getBookType()))
                .body(bookVariantResponseDto);
        }
    }

    @GetMapping
    public ResponseEntity<List<BookVariantDto>> getBookVariants(@PathVariable long bookId) {
        List<BookVariantDto> bookVariants = bookVariantService.findBookVariants(bookId);
        return ResponseEntity.ok(bookVariants);
    }

    @GetMapping("/{bookType}")
    public ResponseEntity<BookVariantDto> getBookVariant(@PathVariable long bookId,
                                                         @PathVariable BookType bookType) {
        BookVariantDto bookVariantDto = bookVariantService.findBookVariant(bookId, bookType);
        return ResponseEntity.ok(bookVariantDto);
    }

    @PutMapping("/{bookType}")
    public ResponseEntity<Void> updateBookVariant(@PathVariable long bookId,
                                                  @PathVariable BookType bookType,
                                                  @Valid @RequestBody BookVariantDto bookVariantDto,
                                                  BindingResult bindingResult)
    throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException bindException) {
                throw bindException;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            bookVariantDto.setBookId(bookId);
            bookVariantDto.setBookType(bookType);
            bookVariantService.updateBookVariant(bookVariantDto);
            return ResponseEntity.noContent().build();
        }
    }
}
