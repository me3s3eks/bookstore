package com.encom.bookstore.dto;

import java.time.LocalDate;

public record UserDTO(String surname,
                      String name,
                      String patronymic,
                      LocalDate dateOfBirth,
                      String email,
                      String login,
                      String password) {
}
