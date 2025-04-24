package com.encom.bookstore.dto;

import com.encom.bookstore.model.Language;

import java.time.LocalDate;
import java.util.Set;

public record BookCreateDto (
    String title,
    String description,
    Set<Long> authorsIds,
    long bookCategoryId,
    long publisherId,
    int edition,
    LocalDate publicationDate,
    Language language,
    int pageCount,
    String series,
    String isbn) {
}
