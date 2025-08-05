package com.encom.bookstore.dto;

import java.math.BigDecimal;

public record PaperBookPropertiesDto(
    int quantityInStock,
    int width,
    int height,
    int depth,
    BigDecimal weight) {
}
