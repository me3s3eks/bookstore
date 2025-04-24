package com.encom.bookstore.controllers;

import com.encom.bookstore.exceptions.UserNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFoundException(UserNotFoundException exception, Model model,
                                              HttpServletResponse response, Locale locale) {
        response.setStatus(HttpStatus.NOT_FOUND.value());
        model.addAttribute("error", messageSource.getMessage("errors.error.user_not_found",
                new Object[]{exception.getMessage()}, locale));
        return "errors/404";
    }
}
