package com.encom.bookstore.dto;

public record BookCategoryCreateDto(
    String name,
    Long parentId) {
}
