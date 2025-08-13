package com.encom.bookstore.dto;

import java.time.LocalDate;
import java.util.Set;

public record UserFilterDto(

    String keyword,

    LocalDate dateOfBirthBefore,

    LocalDate dateOfBirthAfter,

    Boolean deleted,

    Set<Long> roleIds) {
}
