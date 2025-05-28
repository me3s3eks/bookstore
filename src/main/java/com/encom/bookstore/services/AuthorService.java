package com.encom.bookstore.services;


import com.encom.bookstore.dto.AuthorBaseInfoDto;
import com.encom.bookstore.dto.AuthorDto;
import com.encom.bookstore.dto.AuthorRequestDto;
import com.encom.bookstore.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface AuthorService {

    AuthorDto createAuthor(AuthorRequestDto authorRequestDto);

    AuthorDto findAuthor(long id);

    AuthorBaseInfoDto findAuthorBaseInfo(long authorId);

    Page<AuthorBaseInfoDto> findAllAuthors(Pageable pageable);

    Page<AuthorBaseInfoDto> findAllAuthorsByKeyword(Pageable pageable, String keyword);

    Author getAuthor(long authorId);

    List<Author> getAuthors(Set<Long> authorsIds);

    void deleteAuthor(long authorId);

    void updateAuthor(long authorId, AuthorRequestDto authorRequestDto);
}
