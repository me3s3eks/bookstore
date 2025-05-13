package com.encom.bookstore.dto;

import lombok.Data;

import java.util.Optional;

@Data
public class BookCategoryUpdateDto {

    private Optional<String> name;

    private Optional<Long> parentId;

}
