package com.encom.bookstore.dto;

import java.time.LocalDate;

public record UserBaseInfoDto(

    long id,

    LocalDate dateOfBirth,

    String email,

    String login) {
}
