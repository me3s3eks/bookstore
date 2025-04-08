package com.encom.bookstore.controllers;

import com.encom.bookstore.dto.UserDTO;
import com.encom.bookstore.model.User;
import com.encom.bookstore.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("accounts")
public class UsersController {
    private final UserService userService;

   /* @PostMapping("user")
    public boolean createUserAccount(@RequestParam String login) {
        userService.createUser(login);
        return true;
    }*/

    @GetMapping("users")
    public String getUsersList(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "accounts/users";
    }

    @GetMapping("users/new-user")
    public String getCreateUserPage() {
        return "accounts/users/new_user";
    }

    @PostMapping("users/new-user")
    public String createUserAccount(UserDTO userDTO) {
        User user = userService.createUser(userDTO.login(), userDTO.password(), userDTO.name(), userDTO.patronymic(), userDTO.surname(),
                userDTO.dateOfBirth(), userDTO.email());
        return "redirect:/accounts/users/%d".formatted(user.getId());
    }

    @GetMapping("users/{userId:\\d+}")
    public String getUser(@PathVariable("userId") long userId, Model model) {
        model.addAttribute("user", userService.findUser(userId).orElseThrow());
        return "accounts/users/user";
    }
}
