package com.encom.bookstore.services.impl;

import com.encom.bookstore.dto.AuthorBaseInfoDto;
import com.encom.bookstore.dto.AuthorRequestDto;
import com.encom.bookstore.dto.AuthorDto;
import com.encom.bookstore.exceptions.EntityNotFoundException;
import com.encom.bookstore.exceptions.ForeignKeyDeleteConstraintException;
import com.encom.bookstore.mappers.AuthorMapper;
import com.encom.bookstore.model.Author;
import com.encom.bookstore.repositories.AuthorRepository;
import com.encom.bookstore.repositories.BookRepository;
import com.encom.bookstore.services.AuthorService;
import com.encom.bookstore.specifications.AuthorSpecifications;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    private final BookRepository bookRepository;

    private final AuthorMapper authorMapper;

    @Override
    @Transactional
    public AuthorDto createAuthor(AuthorRequestDto authorRequestDto) {
        Author author = authorMapper.authorRequestDtoToAuthor(authorRequestDto);
        author = authorRepository.save(author);
        return authorMapper.authorToAuthorDto(author);
    }

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
    public Page<AuthorBaseInfoDto> findAllAuthors(Pageable pageable) {
        Page<Author> authorsPage = authorRepository.findAll(pageable);
        return authorsPage.map(authorMapper::authorToAuthorBaseInfoDto);
    }

    @Override
    public Page<AuthorBaseInfoDto> findAllAuthorsByKeyword(Pageable pageable, String keyword) {
        if (keyword == null) {
            return findAllAuthors(pageable);
        } else {
            Specification<Author> authorSpecification = getAuthorSpecificationForKeyword(keyword);
            Page<Author> auhotrsPage = authorRepository.findAll(authorSpecification, pageable);
            return auhotrsPage.map(authorMapper::authorToAuthorBaseInfoDto);
        }
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

    @Override
    @Transactional
    public void deleteAuthor(long authorId) {
        Author author = getAuthor(authorId);
        if (bookRepository.existsByAuthorsId(authorId)) {
            throw new ForeignKeyDeleteConstraintException("Book");
        }
        authorRepository.delete(author);
    }

    @Override
    @Transactional
    public void updateAuthor(long authorId, AuthorRequestDto authorRequestDto) {
        Author updatedAuthor = getAuthor(authorId);
        authorMapper.updateAuthorFromDto(authorRequestDto, updatedAuthor);
        authorRepository.save(updatedAuthor);
    }

    private static Specification<Author> getAuthorSpecificationForKeyword(String keyword) {
        return Specification.where(AuthorSpecifications.nameLike(keyword)
            .or(AuthorSpecifications.surnameLike(keyword)));
    }
}
