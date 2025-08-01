package com.encom.bookstore.services.impl;

import com.encom.bookstore.dto.BookVariantDto;
import com.encom.bookstore.exceptions.EntityAlreadyExistsException;
import com.encom.bookstore.exceptions.EntityNotFoundException;
import com.encom.bookstore.exceptions.InvalidRequestDataException;
import com.encom.bookstore.mappers.BookVariantMapper;
import com.encom.bookstore.mappers.PaperBookPropertiesMapper;
import com.encom.bookstore.model.Book;
import com.encom.bookstore.model.BookType;
import com.encom.bookstore.model.BookVariant;
import com.encom.bookstore.model.BookVariantId;
import com.encom.bookstore.repositories.BookVariantRepository;
import com.encom.bookstore.repositories.PaperBookPropertiesRepository;
import com.encom.bookstore.services.BookService;
import com.encom.bookstore.services.BookVariantService;
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
public class DefaultBookVariantService implements BookVariantService {

    private final BookVariantRepository bookVariantRepository;

    private final BookVariantMapper bookVariantMapper;

    private final BookService bookService;

    private final PaperBookPropertiesRepository paperBookPropertiesRepository;

    private final PaperBookPropertiesMapper paperBookPropertiesMapper;

    @Override
    @Transactional
    public BookVariantDto createBookVariant(BookVariantDto bookVariantDto) {
        validateBookVariantDto(bookVariantDto);
        BookVariant bookVariant = bookVariantMapper.bookVariantDtoToBookVariant(bookVariantDto);
        checkBookVariantExists(bookVariant.getId());
        bookVariant = addRelatedBook(bookVariant);
        bookVariant = bookVariantRepository.save(bookVariant);
        return bookVariantMapper.bookVariantToBookVariantDto(bookVariant);
    }

    @Override
    public BookVariantDto findBookVariant(long bookId, BookType bookType) {
        BookVariantId bookVariantId = new BookVariantId(bookId, bookType);
        BookVariant bookVariant = getBookVariant(bookVariantId);
        return bookVariantMapper.bookVariantToBookVariantDto(bookVariant);
    }

    @Override
    public List<BookVariantDto> findBookVariants(long bookId) {
        Book book = bookService.getBook(bookId);
        List<BookVariant> bookVariants =
            bookVariantRepository.findWithPropertiesByBook(book);
        return bookVariants.stream()
            .map(bookVariantMapper::bookVariantToBookVariantDto)
            .collect(Collectors.toList());
    }

    @Override
    public BookVariant getBookVariant(BookVariantId bookVariantId) {
        return bookVariantRepository.findWithPropertiesById(bookVariantId)
            .orElseThrow(() -> new EntityNotFoundException("BookVariant", Set.of(bookVariantId)));
    }

    @Override
    @Transactional
    public void updateBookVariant(BookVariantDto bookVariantDto) {
        validateBookVariantDto(bookVariantDto);
        BookVariantId bookVariantId = getBookVariantIdFromDto(bookVariantDto);
        BookVariant updatedBookVariant = bookVariantRepository.findWithPaperBookPropertiesById(bookVariantId)
            .orElseThrow(() -> new EntityNotFoundException("BookVariant", Set.of(bookVariantId)));
        bookVariantMapper.updateBookVariantFromDto(bookVariantDto, updatedBookVariant);
        bookVariantRepository.save(updatedBookVariant);
    }

    private BookVariant addRelatedBook(BookVariant bookVariant) {
        Book book = bookService.getBook(bookVariant.getId().getBookId());
        bookVariant.setBook(book);
        return bookVariant;
    }

    private void checkBookVariantExists(BookVariantId bookVariantId) {
        if (bookVariantRepository.existsById(bookVariantId)) {
            throw new EntityAlreadyExistsException(bookVariantId);
        }
    }

    private void validateBookVariantDto(BookVariantDto bookVariantDto) {
        if (bookVariantDto.getBookType() == BookType.PAPER_BOOK ||
            bookVariantDto.getBookType() == BookType.HARDCOVER_BOOK) {
            if (bookVariantDto.getPaperBookPropertiesDto() == null) {
                throw new InvalidRequestDataException("bookType", "Paper and hardcover books must have properties");
            }
        } else {
            if (bookVariantDto.getPaperBookPropertiesDto() != null) {
                throw new InvalidRequestDataException("bookType", "Digital book can't have properties");
            }
        }
    }

    private BookVariantId getBookVariantIdFromDto(BookVariantDto bookVariantDto) {
        long bookId = bookVariantDto.getBookId();
        BookType bookType = bookVariantDto.getBookType();
        return new BookVariantId(bookId, bookType);
    }
}