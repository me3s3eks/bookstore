package com.encom.bookstore.dto;

public record BookCategoryDto(
    long id,
    String name,
    Long parentId) {
}
