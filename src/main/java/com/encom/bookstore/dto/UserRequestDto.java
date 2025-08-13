package com.encom.bookstore.dto;

import com.encom.bookstore.constraints.UniqueUserEmail;
import com.encom.bookstore.constraints.UniqueUserLogin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserRequestDto(

    @NotBlank(message = "{accounts.users.new_user.errors.field_is_blank}")
    @Size(max = 50, message = "{accounts.users.new_user.errors.field_size_over_max}")
    String surname,

    @NotBlank(message = "{accounts.users.new_user.errors.field_is_blank}")
    @Size(max = 50, message = "{accounts.users.new_user.errors.field_size_over_max}")
    String name,

    @NotBlank(message = "{accounts.users.new_user.errors.field_is_blank}")
    @Size(max = 50, message = "{accounts.users.new_user.errors.field_size_over_max}")
    String patronymic,

    @NotNull(message = "{accounts.users.new_user.errors.field_is_null}")
    @Past(message = "{accounts.users.new_user.errors.date_of_birth_is_invalid}")
    LocalDate dateOfBirth,

    @NotNull(message = "{accounts.users.new_user.errors.field_is_null}")
    @Size(max = 255, message = "{accounts.users.new_user.errors.field_size_over_max}")
    @Email(message = "{accounts.users.new_user.errors.email_is_invalid}")
    @UniqueUserEmail(message = "{accounts.users.new_user.errors.email_not_unique}")
    String email,

    @NotBlank(message = "{accounts.users.new_user.errors.field_is_blank}")
    @Size(max = 255, message = "{accounts.users.new_user.errors.field_size_over_max}")
    @UniqueUserLogin(message = "{accounts.users.new_user.errors.login_not_unique}")
    String login,

    @NotBlank(message = "{accounts.users.new_user.errors.field_is_blank}")
    @Size(max = 50, message = "{accounts.users.new_user.errors.field_size_over_max}")
    String password) {
}
