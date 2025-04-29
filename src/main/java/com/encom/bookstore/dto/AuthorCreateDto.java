package com.encom.bookstore.dto;

public record AuthorCreateDto(
    String name,
    String surname,
    String about) {
}
