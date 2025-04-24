package com.encom.bookstore.dto;

import com.encom.bookstore.model.Language;

import java.time.LocalDate;
import java.util.List;

public record BookBaseInfoDto(
    long id,
    String title,
    int edition,
    LocalDate publicationDate,
    Language language,
    String isbn,
    List<AuthorBaseInfoDto> authors) {
}
