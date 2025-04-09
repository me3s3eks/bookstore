package com.encom.bookstore.services;

import com.encom.bookstore.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserService {

//    void createUser(String login);

    List<User> findAllUsers();

    User createUser(String login, String password, String name,
                    String patronymic, String surname, LocalDate dateOfBirth, String email);

    Optional<User> findUser(long userId);

    void updateUser(long userId, String surname, String name, String patronymic, LocalDate dateOfBirth);

    void deleteUser(long id);
}
