package com.encom.bookstore.services;

import com.encom.bookstore.dto.UserCreateDTO;
import com.encom.bookstore.dto.UserUpdateDTO;
import com.encom.bookstore.model.User;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Locale;

public interface UserService {

    List<User> findAllUsers();

    User createUser(UserCreateDTO userCreateDTO);

    User findUser(long userId);

    void updateUser(long userId, UserUpdateDTO userUpdateDTO, Model model, Locale locale);

    void deleteUser(long id);
}
