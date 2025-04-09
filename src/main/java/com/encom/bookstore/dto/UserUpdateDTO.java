package com.encom.bookstore.dto;

import java.time.LocalDate;

public record UserUpdateDTO (String surname,
                             String name,
                             String patronymic,
                             LocalDate dateOfBirth) {

}
