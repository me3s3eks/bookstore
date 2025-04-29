package com.encom.bookstore.controllers;

import com.encom.bookstore.dto.AuthorDto;
import com.encom.bookstore.mappers.AuthorMapper;
import com.encom.bookstore.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors/{authorId:\\d+}")
@RequiredArgsConstructor
public class AuthorRestController {

    private final AuthorService authorService;

    private final AuthorMapper authorMapper;

    @GetMapping
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable long authorId) {
        AuthorDto authorDto = authorService.findAuthor(authorId);
        return ResponseEntity.ok(authorDto);
    }
}
