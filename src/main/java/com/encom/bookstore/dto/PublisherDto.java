package com.encom.bookstore.dto;

import com.encom.bookstore.model.Country;

public record PublisherDto(
    long id,
    String name,
    Country country,
    String description) {
}
