package com.encom.bookstore.controllers;

import com.encom.bookstore.dto.UserUpdateDTO;
import com.encom.bookstore.model.User;
import com.encom.bookstore.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

@Controller
@RequiredArgsConstructor
@RequestMapping("/accounts/users/{userId:\\d+}")
public class UserController {
    private final UserService userService;

    @ModelAttribute("user")
    public User addUserToModel(@PathVariable("userId") long userId) {
        return userService.findUser(userId);
    }

    @GetMapping
    public String getUser() {
        return "accounts/users/user";
    }

    @GetMapping("/edit")
    public String getUserEditPage(Model model) {
        model.addAttribute("userUpdateDTO", new UserUpdateDTO());
        return "accounts/users/edit";
    }

    @PostMapping("/edit")
    public String updateUser(@ModelAttribute(value = "user", binding = false) User user,
                             @ModelAttribute("userUpdateDTO") UserUpdateDTO userUpdateDTO,
                             Model model,
                             Locale locale) {
        userService.updateUser(user.getId(), userUpdateDTO, model, locale);
        if (model.containsAttribute("constraintsViolations")) {
            return "accounts/users/edit";
        }
        return "redirect:/accounts/users/%d".formatted(user.getId());
    }

    @PostMapping("/delete")
    public String deleteUser(@ModelAttribute("user") User user) {
        userService.deleteUser(user.getId());
        return "redirect:/accounts/users";
    }
}
