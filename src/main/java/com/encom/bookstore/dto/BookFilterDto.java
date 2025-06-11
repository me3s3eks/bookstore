package com.encom.bookstore.dto;

import com.encom.bookstore.model.Language;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public record BookFilterDto(
    String keyword,
    Set<Long> bookCategoryIds,
    LocalDate publicationDateAfter,
    List<Language> languages,
    Boolean deleted) {
}
