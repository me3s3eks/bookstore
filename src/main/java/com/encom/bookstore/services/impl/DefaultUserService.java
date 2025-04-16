package com.encom.bookstore.services.impl;

import com.encom.bookstore.dto.UserCreateDTO;
import com.encom.bookstore.dto.UserUpdateDTO;
import com.encom.bookstore.exceptions.UserNotFoundException;
import com.encom.bookstore.mappers.UserMapper;
import com.encom.bookstore.model.User;
import com.encom.bookstore.repositories.UserRepository;
import com.encom.bookstore.services.UserService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Validator validator;
    private final MessageSource messageSource;

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
        User newUser = userMapper.userCreateDTOtoUser(userCreateDTO);
        return userRepository.save(newUser);
    }

    @Override
    public User findUser(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("" + userId));
    }

    @Override
    @Transactional
    public void updateUser(long userId, UserUpdateDTO userUpdateDTO, Model model, Locale locale) {
        User user = findUser(userId);
        Map<String, List<String>> constraintsViolations = validateUserUpdateDTO(user, userUpdateDTO, locale);
        if (constraintsViolations.isEmpty()) {
            userMapper.updateUserFromUserUpdateDTO(userUpdateDTO, user);
            userRepository.save(user);
        } else {
            model.addAttribute("constraintsViolations", constraintsViolations);
        }
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    private Map<String, List<String>> validateUserUpdateDTO(User user, UserUpdateDTO userUpdateDTO, Locale locale) {
        Set<ConstraintViolation<UserUpdateDTO>> constraintsViolations = validator.validate(userUpdateDTO);
        Map<String, List<String>> constraintsViolationsMap = new HashMap<>();
        constraintsViolations.stream()
                .forEach(constraintViolation ->
                constraintsViolationsMap.computeIfAbsent(constraintViolation.getPropertyPath().toString(),
                        k -> new ArrayList<>()).add(constraintViolation.getMessage()));

        if (!user.getLogin().equals(userUpdateDTO.getLogin()) && userRepository.existsByLogin(userUpdateDTO.getLogin())) {
            constraintsViolationsMap.computeIfAbsent("login",
                    k -> new ArrayList<>()).add(messageSource.getMessage("accounts.users.edit.errors.login_not_unique", null, locale));
        }
        if (!user.getEmail().equals(userUpdateDTO.getEmail()) && userRepository.existsByEmailIgnoreCase(userUpdateDTO.getEmail())) {
            constraintsViolationsMap.computeIfAbsent("email",
                    k -> new ArrayList<>()).add(messageSource.getMessage("accounts.users.edit.errors.email_not_unique", null, locale));
        }
        return constraintsViolationsMap;
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
