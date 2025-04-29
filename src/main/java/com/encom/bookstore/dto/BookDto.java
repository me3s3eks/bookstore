package com.encom.bookstore.dto;

import com.encom.bookstore.model.Language;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record BookDto(
    long id,
    String title,
    String description,
    List<AuthorBaseInfoDto> authors,
    BookCategoryDto bookCategory,
    PublisherBaseInfoDto publisher,
    int edition,
    LocalDate publicationDate,
    Language language,
    int pageCount,
    String series,
    String isbn,
    LocalDateTime timeOfRemoval) {
}
