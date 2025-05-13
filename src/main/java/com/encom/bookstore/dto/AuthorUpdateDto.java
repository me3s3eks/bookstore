package com.encom.bookstore.dto;

import lombok.Data;

import java.util.Optional;

@Data
public class AuthorUpdateDto {

    private Optional<String> name;

    private Optional<String> surname;

    private Optional<String> about;
}
