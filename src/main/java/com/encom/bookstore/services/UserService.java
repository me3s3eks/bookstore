package com.encom.bookstore.services;

import com.encom.bookstore.dto.UserCreateDTO;
import com.encom.bookstore.dto.UserUpdateDTO;
import com.encom.bookstore.model.User;

import java.util.List;

public interface UserService {

//    void createUser(String login);

    List<User> findAllUsers();

    User createUser(UserCreateDTO userCreateDTO);

    User findUser(long userId);

    void updateUser(long userId, UserUpdateDTO userUpdateDTO);

    void deleteUser(long id);
}
