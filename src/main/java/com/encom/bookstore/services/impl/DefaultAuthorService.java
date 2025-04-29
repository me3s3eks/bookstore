package com.encom.bookstore.services.impl;

import com.encom.bookstore.dto.AuthorBaseInfoDto;
import com.encom.bookstore.dto.AuthorDto;
import com.encom.bookstore.exceptions.EntityNotFoundException;
import com.encom.bookstore.mappers.AuthorMapper;
import com.encom.bookstore.model.Author;
import com.encom.bookstore.repositories.AuthorRepository;
import com.encom.bookstore.services.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultAuthorService implements AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    @Override
    public AuthorDto findAuthor(long authorId) {
        Author author = getAuthor(authorId);
        return authorMapper.authorToAuthorDto(author);
    }

    @Override
    public AuthorBaseInfoDto findAuthorBaseInfo(long authorId) {
        Author author = getAuthor(authorId);
        return authorMapper.authorToAuthorBaseInfoDto(author);
    }

    @Override
    public Author getAuthor(long authorId) {
        return authorRepository.findById(authorId)
            .orElseThrow(() -> new EntityNotFoundException("Author",
                Set.of(authorId)));
    }

    @Override
    public List<Author> getAuthors(Set<Long> authorsIds) {
        List<Author> authors = authorRepository.findAllById(authorsIds);
        if (authors.size() != authorsIds.size()) {
            Set<Long> foundIds = authors.stream().map(Author::getId).collect(Collectors.toSet());
            throw new EntityNotFoundException("Author",
                authorsIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .collect(Collectors.toSet()));

        }
        return authors;
    }
}
