package com.encom.bookstore.services;

import com.encom.bookstore.dto.UserCreateDto;
import com.encom.bookstore.dto.UserUpdateDto;
import com.encom.bookstore.model.User;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Locale;

public interface UserService {

    List<User> findAllUsers();

    User createUser(UserCreateDto userCreateDto);

    User findUser(long userId);

    void updateUser(long userId, UserUpdateDto userUpdateDto, Model model, Locale locale);

    void deleteUser(long id);
}
