package com.encom.bookstore.dto;

import com.encom.bookstore.validators.UniqueUserEmail;
import com.encom.bookstore.validators.UniqueUserLogin;
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
public class UserCreateDTO {
        @NotBlank(message = "{accounts.users.new_user.errors.field_is_blank}")
        @Size(max = 50, message = "{accounts.users.new_user.errors.field_size_over_max}")
        private String surname;

        @NotBlank(message = "{accounts.users.new_user.errors.field_is_blank}")
        @Size(max = 50, message = "{accounts.users.new_user.errors.field_size_over_max}")
        private String name;

        @NotBlank(message = "{accounts.users.new_user.errors.field_is_blank}")
        @Size(max = 50, message = "{accounts.users.new_user.errors.field_size_over_max}")
        private String patronymic;

        @NotNull(message = "{accounts.users.new_user.errors.field_is_null}")
        @Past(message = "{accounts.users.new_user.errors.date_of_birth_is_invalid}")
        private LocalDate dateOfBirth;

        @NotNull(message = "{accounts.users.new_user.errors.field_is_null}")
        @Size(max = 255, message = "{accounts.users.new_user.errors.field_size_over_max}")
        @Email(message = "{accounts.users.new_user.errors.email_is_invalid}")
        @UniqueUserEmail(message = "{accounts.users.new_user.errors.email_not_unique}")
        private String email;

        @NotBlank(message = "{accounts.users.new_user.errors.field_is_blank}")
        @Size(max = 255, message = "{accounts.users.new_user.errors.field_size_over_max}")
        @UniqueUserLogin(message = "{accounts.users.new_user.errors.login_not_unique}")
        private String login;

        @NotBlank(message = "{accounts.users.new_user.errors.field_is_blank}")
        @Size(max = 50, message = "{accounts.users.new_user.errors.field_size_over_max}")
        private String password;
}
