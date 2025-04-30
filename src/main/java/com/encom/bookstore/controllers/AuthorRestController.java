package com.encom.bookstore.controllers;

import com.encom.bookstore.dto.AuthorBaseInfoDto;
import com.encom.bookstore.dto.AuthorDto;
import com.encom.bookstore.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalogue/authors")
@RequiredArgsConstructor
public class AuthorRestController {

    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<Page<AuthorBaseInfoDto>> getAllAuthors(Pageable pageable) {
        Page<AuthorBaseInfoDto> authorPage = authorService.findAllAuthors(pageable);
        return ResponseEntity.ok(authorPage);
    }

    @GetMapping("/{authorId:\\d+}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable long authorId) {
        AuthorDto authorDto = authorService.findAuthor(authorId);
        return ResponseEntity.ok(authorDto);
    }
}
