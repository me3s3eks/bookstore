package com.encom.bookstore.controllers;

import com.encom.bookstore.dto.UserCreateDto;
import com.encom.bookstore.model.User;
import com.encom.bookstore.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class UsersController {
    private final UserService userService;

   /* @PostMapping("user")
    public boolean createUserAccount(@RequestParam String login) {
        userService.createUser(login);
        return true;
    }*/

    @GetMapping("/users")
    public String getUsersList(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "accounts/users";
    }

    @GetMapping("/users/new-user")
    public String getCreateUserPage(Model model) {
        model.addAttribute("userCreateDto", new UserCreateDto());
        return "accounts/users/new_user";
    }

    @PostMapping("/users/new-user")
    public String createUserAccount(@Valid @ModelAttribute("userCreateDto") UserCreateDto userCreateDto,
                                    BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "accounts/users/new_user";
        } else {
            User user = userService.createUser(userCreateDto);
            return "redirect:/accounts/users/%d".formatted(user.getId());
        }
    }
}
