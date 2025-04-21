package com.encom.bookstore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {
    @NotBlank(message = "{accounts.users.user.edit.errors.field_is_blank}")
    @Size(max = 50, message = "{accounts.users.user.edit.errors.field_is_blank}")
    private String surname;

    @NotBlank(message = "{accounts.users.user.edit.errors.field_is_blank}")
    @Size(max = 50, message = "{accounts.users.user.edit.errors.field_is_blank}")
    private String name;

    @NotBlank(message = "{accounts.users.user.edit.errors.field_is_blank}")
    @Size(max = 50, message = "{accounts.users.user.edit.errors.field_is_blank}")
    private String patronymic;

    @NotNull(message = "{accounts.users.user.edit.errors.field_is_null}")
    @Past(message = "{accounts.users.user.edit.errors.date_of_birth_is_invalid}")
    private LocalDate dateOfBirth;

    @NotNull(message = "{accounts.users.user.edit.errors.field_is_null}")
    @Size(max = 255, message = "{accounts.users.user.edit.errors.field_is_blank}")
    @Email(message = "{accounts.users.user.edit.errors.email_is_invalid}")
    private String email;

    @NotBlank(message = "{accounts.users.user.edit.errors.field_is_blank}")
    @Size(max = 255, message = "{accounts.users.user.edit.errors.field_is_blank}")
    private String login;
}
