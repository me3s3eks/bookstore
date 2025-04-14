package com.encom.bookstore.controllers;

import com.encom.bookstore.dto.UserCreateDTO;
import com.encom.bookstore.model.User;
import com.encom.bookstore.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.Date;

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
        model.addAttribute("userCreateDTO", new UserCreateDTO());
        return "accounts/users/new_user";
    }

    @PostMapping("/users/new-user")
    public String createUserAccount(@Valid @ModelAttribute("userCreateDTO") UserCreateDTO userCreateDTO,
                                    BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "accounts/users/new_user";
        } else {
            User user = userService.createUser(userCreateDTO);
            return "redirect:/accounts/users/%d".formatted(user.getId());
        }
    }
}
