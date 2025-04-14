package com.encom.bookstore.controllers;

import com.encom.bookstore.dto.UserUpdateDTO;
import com.encom.bookstore.model.User;
import com.encom.bookstore.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
                             @Valid @ModelAttribute("userUpdateDTO") UserUpdateDTO userUpdateDTO,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "accounts/users/edit";
        } else {
            userService.updateUser(user.getId(), userUpdateDTO);
            return "redirect:/accounts/users/%d".formatted(user.getId());
        }
    }

    @PostMapping("/delete")
    public String deleteUser(@ModelAttribute("user") User user) {
        userService.deleteUser(user.getId());
        return "redirect:/accounts/users";
    }
}
