package com.encom.bookstore.dto;

import com.encom.bookstore.model.Country;

public record PublisherRequestDto(
    String name,
    Country country,
    String description) {
}
