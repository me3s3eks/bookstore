package com.encom.bookstore.dto;

import com.encom.bookstore.model.Language;
import lombok.Data;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Data
public class BookUpdateDto {

    private Optional<String> title;

    private Optional<String> description;

    private Optional<Set<Long>> authorsIds;

    private Optional<Long> bookCategoryId;

    private Optional<Long> publisherId;

    private Optional<Integer> edition;

    private Optional<LocalDate> publicationDate;

    private Optional<Language> language;

    private Optional<Integer> pageCount;

    private Optional<String> series;

    private Optional<String> isbn;
}
