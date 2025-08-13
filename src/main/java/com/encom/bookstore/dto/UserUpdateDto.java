package com.encom.bookstore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserUpdateDto(

    @Size(min = 1, max = 50, message = "{accounts.users.new_user.errors.invalid_field_size}")
    String surname,

    @Size(min = 1, max = 50, message = "{accounts.users.new_user.errors.invalid_field_size}")
    String name,

    @Size(min = 1, max = 50, message = "{accounts.users.new_user.errors.invalid_field_size}")
    String patronymic,

    @Past(message = "{accounts.users.user.edit.errors.date_of_birth_is_invalid}")
    LocalDate dateOfBirth,

    @Size(max = 255, message = "{accounts.users.new_user.errors.field_size_over_max}")
    @Email(message = "{accounts.users.user.edit.errors.email_is_invalid}")
    String email,

    @Size(min = 1, max = 255, message = "{accounts.users.new_user.errors.invalid_field_size}")
    String login,

    @Size(min = 1, max = 50, message = "{accounts.users.new_user.errors.invalid_field_size}")
    String currentPassword,

    @Size(min = 1, max = 50, message = "{accounts.users.new_user.errors.invalid_field_size}")
    String newPassword) {
}
