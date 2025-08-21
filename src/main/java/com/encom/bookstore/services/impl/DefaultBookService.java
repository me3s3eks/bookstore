package com.encom.bookstore.services.impl;

import com.encom.bookstore.dto.BookBaseInfoDto;
import com.encom.bookstore.dto.BookDto;
import com.encom.bookstore.dto.BookFilterDto;
import com.encom.bookstore.dto.BookRequestDto;
import com.encom.bookstore.exceptions.EntityNotFoundException;
import com.encom.bookstore.mappers.BookMapper;
import com.encom.bookstore.model.Author;
import com.encom.bookstore.model.Book;
import com.encom.bookstore.model.BookCategory;
import com.encom.bookstore.model.Publisher;
import com.encom.bookstore.repositories.BookRepository;
import com.encom.bookstore.security.UserRole;
import com.encom.bookstore.services.AuthorService;
import com.encom.bookstore.services.BookCategoryService;
import com.encom.bookstore.services.BookService;
import com.encom.bookstore.services.PublisherService;
import com.encom.bookstore.specifications.BookSpecifications;
import com.encom.bookstore.utils.SecurityUtils;
import com.encom.bookstore.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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
    public BookDto createBook(BookRequestDto bookRequestDto) {
        Book book = bookMapper.bookRequestDtoToBook(bookRequestDto);
        addRelatedEntities(book, bookRequestDto);
        book = bookRepository.save(book);
        return bookMapper.bookToBookDto(book);
    }

    @Override
    public BookDto findBook(long bookId) {
        Book book = getBook(bookId);
        return bookMapper.bookToBookDto(book);
    }

    @Override
    public Page<BookBaseInfoDto> findBooksByAuthor(long authorId, Pageable pageable) {
        Specification<Book> spec = BookSpecifications.withAuthorId(authorId);
        Page<Book> booksPage = bookRepository.findAll(spec, pageable);
        return booksPage.map(bookMapper::bookToBookBaseInfoDto);
    }

    @Override
    public Page<BookBaseInfoDto> findBooksByFilterDto(Pageable pageable, BookFilterDto bookFilterDto) {
        Specification<Book> bookSpec = parseFilterDtoToSpecification(bookFilterDto);
        return findBooksBySpecification(bookSpec, pageable);
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
    public void restoreBook(long bookId) {
        Book book = getBook(bookId);
        if (book.getTimeOfRemoval() != null) {
            book.setTimeOfRemoval(null);
        }
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void updateBook(long bookId, BookRequestDto bookRequestDto) {
        Book updatedBook = bookRepository.findById(bookId).orElseThrow(
            () -> new EntityNotFoundException("Book", Set.of(bookId))
        );
        bookMapper.updateBookFromDtoWithoutRelatedEntities(bookRequestDto, updatedBook);
        updateRelatedEntities(updatedBook, bookRequestDto);
        bookRepository.save(updatedBook);
    }

    private Specification<Book> parseFilterDtoToSpecification(BookFilterDto bookFilterDto) {
        Specification<Book> spec = Specification
            .where((root, query, cb) -> cb.conjunction());

        if (bookFilterDto == null) {
            if (SecurityUtils.userHasRole(UserRole.ROLE_USER.name())) {
                spec = spec.and(BookSpecifications.isDeleted(false));
            }
            return spec;
        }

        if (bookFilterDto.keyword() != null) {
            String keyword = bookFilterDto.keyword();
            if (StringUtils.hasValidIsbn(keyword)) {
                spec = spec.and(BookSpecifications.byIsbn(StringUtils.normalizeIsbn(keyword)));
            } else {
                spec = spec.and(
                    BookSpecifications.titleLike(bookFilterDto.keyword())
                        .or(BookSpecifications.withAuthorNameOrSurnameLike(bookFilterDto.keyword()))
                );
            }
        }
        if (bookFilterDto.bookCategoryIds() != null) {
            Set<Long> categoryIdsForFiltering =
                bookCategoryService.findBookCategoryIdsInSubtree(bookFilterDto.bookCategoryIds());
            spec = spec.and(BookSpecifications.withBookCategoriesIn(categoryIdsForFiltering));
        }
        if (bookFilterDto.publicationDateAfter() != null) {
            spec = spec.and(BookSpecifications.publicationDateOnOrAfter(bookFilterDto.publicationDateAfter()));
        }
        if (bookFilterDto.languages() != null) {
            spec = spec.and(BookSpecifications.languageIn(bookFilterDto.languages()));
        }

        if (SecurityUtils.userHasRole(UserRole.ROLE_MANAGER.name())) {
            if (bookFilterDto.deleted() != null) {
                spec = spec.and(BookSpecifications.isDeleted(bookFilterDto.deleted()));
            }
        } else {
            spec = spec.and(BookSpecifications.isDeleted(false));
        }

        return spec;
    }

    private Page<BookBaseInfoDto> findBooksBySpecification(Specification<Book> specification, Pageable pageable) {
        Page<Long> bookIds = bookRepository.findIdsBySpecification(specification, pageable);
        List<Book> books = bookRepository.findWithAuthorsByIdIn(bookIds.getContent());
        long totalElements = bookIds.getTotalElements();
        Page<Book> booksPage = new PageImpl<>(books, pageable, totalElements);
        return booksPage.map(bookMapper::bookToBookBaseInfoDto);
    }

    private Book addRelatedEntities(Book book, BookRequestDto bookRequestDto) {
        List<Author> authors = authorService.getAuthors(bookRequestDto.authorsIds());
        BookCategory category = bookCategoryService.getBookCategory(bookRequestDto.bookCategoryId());
        Publisher publisher = publisherService.getPublisher(bookRequestDto.publisherId());

        book.setAuthors(authors);
        book.setBookCategory(category);
        book.setPublisher(publisher);

        return book;
    }

    private Book updateRelatedEntities(Book book, BookRequestDto bookRequestDto) {
        if (bookRequestDto.authorsIds() != null) {
            Set<Long> authorsIds = bookRequestDto.authorsIds();
            if (!authorsIds.isEmpty()) {
                List<Author> authors = authorService.getAuthors(authorsIds);
                book.setAuthors(authors);
            }
        }

        BookCategory category = bookCategoryService.getBookCategory(bookRequestDto.bookCategoryId());
        book.setBookCategory(category);

        Publisher publisher = publisherService.getPublisher(bookRequestDto.publisherId());
        book.setPublisher(publisher);

        return book;
    }
}
