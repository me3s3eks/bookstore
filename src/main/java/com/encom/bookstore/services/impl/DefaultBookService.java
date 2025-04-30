package com.encom.bookstore.services.impl;

import com.encom.bookstore.dto.BookBaseInfoDto;
import com.encom.bookstore.dto.BookCreateDto;
import com.encom.bookstore.dto.BookDto;
import com.encom.bookstore.dto.BookUpdateDto;
import com.encom.bookstore.exceptions.EntityNotFoundException;
import com.encom.bookstore.mappers.BookMapper;
import com.encom.bookstore.model.Author;
import com.encom.bookstore.model.Book;
import com.encom.bookstore.model.BookCategory;
import com.encom.bookstore.model.Publisher;
import com.encom.bookstore.repositories.BookRepository;
import com.encom.bookstore.services.AuthorService;
import com.encom.bookstore.services.BookCategoryService;
import com.encom.bookstore.services.BookService;
import com.encom.bookstore.services.PublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultBookService implements BookService {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    private final AuthorService authorService;

    private final BookCategoryService bookCategoryService;

    private final PublisherService publisherService;

    @Override
    @Transactional
    public BookDto createBook(BookCreateDto bookCreateDto) {
        Book book = bookMapper.bookCreateDtoToBook(bookCreateDto);
        addRelatedEntities(book, bookCreateDto);
        book = bookRepository.save(book);
        return bookMapper.bookToBookDto(book);
    }

    @Override
    public BookDto findBook(long bookId) {
        Book book = getBook(bookId);
        return bookMapper.bookToBookDto(book);
    }

    @Override
    public Page<BookBaseInfoDto> findAllBooks(Pageable pageable) {
        Page<Long> bookIds = bookRepository.findAllIds(pageable);
        List<Book> books = bookRepository.findAllWithAuthorsByIdIn(bookIds.getContent());
        long totalElements = bookRepository.count();
        Page<Book> booksPage = new PageImpl<>(books, pageable, totalElements);
        return booksPage.map(bookMapper::bookToBookBaseInfoDto);
    }

    @Override
    public Book getBook(long bookId) {
        return bookRepository.findWithAllRelatedEntitiesById(bookId)
            .orElseThrow(() -> new EntityNotFoundException("Book", Set.of(bookId)));
    }

    @Override
    @Transactional
    public void deleteBook(long bookId) {
        Book book = getBook(bookId);
        book.setTimeOfRemoval(LocalDateTime.now(ZoneOffset.UTC));
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void updateBook(long bookId, BookUpdateDto bookUpdateDto) {
        Book updatedBook = bookRepository.findById(bookId).orElseThrow(
            () -> new EntityNotFoundException("Book", Set.of(bookId))
        );
        bookMapper.updateBookFromDtoWithoutRelatedEntities(bookUpdateDto, updatedBook);
        updateRelatedEntities(updatedBook, bookUpdateDto);
        bookRepository.save(updatedBook);
    }

    @Override
    @Transactional
    public void restoreBook(long bookId) {
        Book book = getBook(bookId);
        if (book.getTimeOfRemoval() != null) {
            book.setTimeOfRemoval(null);
        }
        bookRepository.save(book);
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

    private Book updateRelatedEntities(Book book, BookUpdateDto bookUpdateDto) {
        if (bookUpdateDto.getAuthorsIds() != null && bookUpdateDto.getAuthorsIds().isPresent()) {
            Set<Long> authorsIds = bookUpdateDto.getAuthorsIds().get();
            if (!authorsIds.isEmpty()) {
                List<Author> authors = authorService.getAuthors(authorsIds);
                book.setAuthors(authors);
            }
        }
        if (bookUpdateDto.getBookCategoryId() != null && bookUpdateDto.getBookCategoryId().isPresent()) {
            BookCategory category =
                bookCategoryService.getBookCategory(bookUpdateDto.getBookCategoryId().get());
            book.setBookCategory(category);
        }
        if (bookUpdateDto.getPublisherId() != null && bookUpdateDto.getPublisherId().isPresent()) {
            Publisher publisher = publisherService.getPublisher(bookUpdateDto.getPublisherId().get());
            book.setPublisher(publisher);
        }
        return book;
    }
}
