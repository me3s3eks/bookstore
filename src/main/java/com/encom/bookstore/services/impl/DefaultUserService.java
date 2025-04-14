package com.encom.bookstore.services.impl;

import com.encom.bookstore.dto.UserCreateDTO;
import com.encom.bookstore.dto.UserUpdateDTO;
import com.encom.bookstore.exceptions.UserNotFoundException;
import com.encom.bookstore.model.User;
import com.encom.bookstore.repositories.UserRepository;
import com.encom.bookstore.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
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
    @Transactional
    public User createUser(UserCreateDTO userCreateDTO) {
        return userRepository.save(new User(null, userCreateDTO.getLogin(), userCreateDTO.getPassword(), userCreateDTO.getName(),
                userCreateDTO.getPatronymic(), userCreateDTO.getSurname(), userCreateDTO.getDateOfBirth(), userCreateDTO.getEmail(),
                null, null));
    }

    @Override
    public User findUser(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("" + userId));
    }

    @Override
    @Transactional
    public void updateUser(long userId, UserUpdateDTO userUpdateDTO) {
        User user = findUser(userId);
        user.setSurname(userUpdateDTO.getSurname());
        user.setName(userUpdateDTO.getName());
        user.setPatronymic(userUpdateDTO.getPatronymic());
        user.setDateOfBirth(userUpdateDTO.getDateOfBirth());
        user.setEmail(userUpdateDTO.getEmail());
        user.setLogin(userUpdateDTO.getLogin());
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userRepository.deleteById(id);
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
