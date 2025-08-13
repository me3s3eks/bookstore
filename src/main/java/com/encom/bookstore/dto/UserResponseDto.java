package com.encom.bookstore.dto;

import java.time.LocalDate;

public record UserResponseDto (
    long id,

    String surname,

    String name,

    String patronymic,

    LocalDate dateOfBirth,

    String email,

    String login) {
}
