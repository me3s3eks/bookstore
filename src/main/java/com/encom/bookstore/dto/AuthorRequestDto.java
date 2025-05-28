package com.encom.bookstore.dto;

public record AuthorRequestDto(
    String name,
    String surname,
    String about) {
}
