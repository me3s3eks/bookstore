package com.encom.bookstore.services.impl;

import com.encom.bookstore.dto.BookBaseInfoDto;
import com.encom.bookstore.dto.BookCreateDto;
import com.encom.bookstore.dto.BookDto;
import com.encom.bookstore.mappers.BookMapper;
import com.encom.bookstore.model.Author;
import com.encom.bookstore.model.Book;
import com.encom.bookstore.model.BookCategory;
import com.encom.bookstore.model.Publisher;
import com.encom.bookstore.repositories.BookCatalogueRepository;
import com.encom.bookstore.services.AuthorService;
import com.encom.bookstore.services.BookCatalogueService;
import com.encom.bookstore.services.BookCategoryService;
import com.encom.bookstore.services.PublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultBookCatalogueService implements BookCatalogueService {

    private final BookCatalogueRepository bookCatalogueRepository;

    private final BookMapper bookMapper;

    private final AuthorService authorService;

    private final BookCategoryService bookCategoryService;

    private final PublisherService publisherService;

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
        addRelatedEntities(book, bookCreateDto);
        book = bookCatalogueRepository.save(book);
        return bookMapper.bookToBookDto(book);
    }

    private Book addRelatedEntities(Book book, BookCreateDto bookCreateDto) {
        List<Author> authors = authorService.getAuthors(bookCreateDto.authorsIds());
        BookCategory category = bookCategoryService.getBookCategory(bookCreateDto.bookCategoryId());
        Publisher publisher = publisherService.getPublisher(bookCreateDto.publisherId());

        book.setAuthors(authors);
        book.setBookCategory(category);
        book.setPublisher(publisher);

        return book;
    }
}