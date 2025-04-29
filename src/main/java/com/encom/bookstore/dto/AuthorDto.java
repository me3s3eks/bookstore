package com.encom.bookstore.dto;

public record AuthorDto(
    long id,
    String name,
    String surname,
    String about) {
}
