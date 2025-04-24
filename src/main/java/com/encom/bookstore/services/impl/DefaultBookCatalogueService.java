package com.encom.bookstore.services.impl;

import com.encom.bookstore.dto.BookBaseInfoDto;
import com.encom.bookstore.dto.BookCreateDto;
import com.encom.bookstore.dto.BookDto;
import com.encom.bookstore.exceptions.EntityNotFoundException;
import com.encom.bookstore.mappers.BookMapper;
import com.encom.bookstore.model.Author;
import com.encom.bookstore.model.Book;
import com.encom.bookstore.model.BookCategory;
import com.encom.bookstore.model.Publisher;
import com.encom.bookstore.repositories.AuthorRepository;
import com.encom.bookstore.repositories.BookCatalogueRepository;
import com.encom.bookstore.repositories.BookCategoryRepository;
import com.encom.bookstore.repositories.PublisherRepository;
import com.encom.bookstore.services.BookCatalogueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultBookCatalogueService implements BookCatalogueService {
    private final BookCatalogueRepository bookCatalogueRepository;
    private final BookMapper bookMapper;
    private final AuthorRepository authorRepository;
    private final BookCategoryRepository bookCategoryRepository;
    private final PublisherRepository publisherRepository;
    private final MessageSource messageSource;

    @Override
    public Page<BookBaseInfoDto> findAllBooks(Pageable pageable) {
        Page<Long> bookIds = bookCatalogueRepository.findAllIds(pageable);
        List<Book> books = bookCatalogueRepository.findAllWithAuthorsByIdIn(bookIds.getContent());
        long totalElements = bookCatalogueRepository.count();
        Page<Book> booksPage = new PageImpl<>(books, pageable, totalElements);
        return booksPage.map(bookMapper::bookToBookBaseInfoDto);
    }

    @Override
    @Transactional
    public BookDto createBook(BookCreateDto bookCreateDto) {
        Book book = bookMapper.bookCreateDtoToBook(bookCreateDto);
        addRelatedEntities(bookCreateDto, book);
        book = bookCatalogueRepository.save(book);
        return bookMapper.bookToBookDto(book);
    }

    private void addRelatedEntities(BookCreateDto bookCreateDto, Book book) {
        List<Author> authors = authorRepository.findAllById(bookCreateDto.authorsIds());
        if (authors.size() != bookCreateDto.authorsIds().size()) {
            Set<Long> foundIds = authors.stream().map(Author::getId).collect(Collectors.toSet());
            throw new EntityNotFoundException("Author",
                bookCreateDto.authorsIds().stream()
                    .filter(id -> !foundIds.contains(id))
                    .collect(Collectors.toSet()));
        }
        BookCategory category = bookCategoryRepository.findById(bookCreateDto.bookCategoryId())
            .orElseThrow(() -> new EntityNotFoundException("BookCategory",
                Set.of(bookCreateDto.bookCategoryId())));
        Publisher publisher = publisherRepository.findById(bookCreateDto.publisherId())
            .orElseThrow(() -> new EntityNotFoundException("Publisher",
                Set.of(bookCreateDto.publisherId())));

        book.setAuthors(authors);
        book.setBookCategory(category);
        book.setPublisher(publisher);
    }
}