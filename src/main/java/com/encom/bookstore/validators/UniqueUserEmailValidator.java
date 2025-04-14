package com.encom.bookstore.validators;

import com.encom.bookstore.model.User;
import com.encom.bookstore.repositories.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueUserEmailValidator implements ConstraintValidator<UniqueUserEmail, String> {
    private final UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !userRepository.existsByEmail(value);
    }
}
