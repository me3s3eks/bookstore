package com.encom.bookstore.services.impl;

import com.encom.bookstore.model.User;
import com.encom.bookstore.repositories.UserRepository;
import com.encom.bookstore.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;

   /* @Override
    public void createUser(String login) {

    }*/

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(String login, String password, String name, String patronymic, String surname,
                           LocalDate dateOfBirth, String email) {
        return userRepository.save(new User(null, login, password, name, patronymic, surname, dateOfBirth, email, null, null));
    }

    @Override
    public Optional<User> findUser(long userId) {
        return userRepository.findById(userId);
    }

    /*public User createUser(String login) {
        User user = User.builder().login(login).password("123456").build();
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Page<User> getPageWithUsers(int pageNumber, int pageSize, String sortField, String sortDirection) {
        Sort sortOptions = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField);

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sortOptions);
        return userRepository.findAll(pageable);
    }*/
}
