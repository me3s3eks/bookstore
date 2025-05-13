package com.encom.bookstore.controllers;

import com.encom.bookstore.dto.AuthorBaseInfoDto;
import com.encom.bookstore.dto.AuthorCreateDto;
import com.encom.bookstore.dto.AuthorDto;
import com.encom.bookstore.dto.AuthorUpdateDto;
import com.encom.bookstore.services.AuthorService;
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
@RequestMapping("/catalogue/authors")
@RequiredArgsConstructor
public class AuthorsRestController {

    private final AuthorService authorService;

    @GetMapping
        public ResponseEntity<Page<AuthorBaseInfoDto>> getAllAuthors(
            @RequestParam(name = "keyword", required = false) String keyword,
            Pageable pageable) {
        Page<AuthorBaseInfoDto> authorPage = null;
        if (keyword == null) {
            authorPage = authorService.findAllAuthors(pageable);
        } else {
            authorPage = authorService.findAllAuthorsByKeyword(pageable, keyword);
        }
        return ResponseEntity.ok(authorPage);
    }

    @PostMapping
    public ResponseEntity<AuthorDto> createAuthor(@Valid @RequestBody AuthorCreateDto authorCreateDto,
                                                  BindingResult bindingResult,
                                                  UriComponentsBuilder uriBuilder)
        throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException bindException) {
                throw bindException;
            } else {
                throw new BindException(bindingResult);
            }
        }
        AuthorDto authorDto = authorService.createAuthor(authorCreateDto);
        return ResponseEntity
            .created(uriBuilder
                .replacePath("/management/catalogue/authors/{authorId}")
                .build(authorDto.id()))
            .body(authorDto);
    }

    @GetMapping("/{authorId:\\d+}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable long authorId) {
        AuthorDto authorDto = authorService.findAuthor(authorId);
        return ResponseEntity.ok(authorDto);
    }

    @PatchMapping("/{authorId:\\d+}")
    public ResponseEntity<Void> updateAuthor(@PathVariable long authorId,
                                             @Valid @RequestBody AuthorUpdateDto authorUpdateDto,
                                             BindingResult bindingResult)
        throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException bindException) {
                throw bindException;
            } else {
                throw new BindException(bindingResult);
            }
        } else {
            authorService.updateAuthor(authorId, authorUpdateDto);
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{authorId:\\d+}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable long authorId) {
        authorService.deleteAuthor(authorId);
        return ResponseEntity.noContent().build();
    }
}
