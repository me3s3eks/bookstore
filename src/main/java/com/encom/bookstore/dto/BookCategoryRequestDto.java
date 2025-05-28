package com.encom.bookstore.dto;

public record BookCategoryRequestDto(
    String name,
    Long parentId) {
}
