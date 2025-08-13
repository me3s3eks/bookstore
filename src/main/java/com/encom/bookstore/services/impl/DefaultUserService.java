package com.encom.bookstore.services.impl;

import com.encom.bookstore.dto.RoleBaseInfoDto;
import com.encom.bookstore.dto.UserBaseInfoDto;
import com.encom.bookstore.dto.UserFilterDto;
import com.encom.bookstore.dto.UserRequestDto;
import com.encom.bookstore.dto.UserResponseDto;
import com.encom.bookstore.dto.UserRolesUpdateDto;
import com.encom.bookstore.dto.UserUpdateDto;
import com.encom.bookstore.exceptions.InvalidPasswordException;
import com.encom.bookstore.exceptions.UserNotFoundException;
import com.encom.bookstore.mappers.RoleMapper;
import com.encom.bookstore.mappers.UserMapper;
import com.encom.bookstore.model.Role;
import com.encom.bookstore.model.User;
import com.encom.bookstore.repositories.UserRepository;
import com.encom.bookstore.security.UserRole;
import com.encom.bookstore.services.RoleService;
import com.encom.bookstore.services.UserService;
import com.encom.bookstore.specifications.UserSpecifications;
import com.encom.bookstore.utils.SecurityUtils;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final Validator validator;

    private final MessageSource messageSource;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    private final RoleMapper roleMapper;

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        if (!SecurityUtils.isUserAuthenticated() || SecurityUtils.userHasRole(UserRole.ROLE_ADMIN.name())) {
            User user = userMapper.userRequestDtoToUser(userRequestDto);
            setHashedPassword(user, userRequestDto.password());
            user = userRepository.save(user);
            if (!SecurityUtils.isUserAuthenticated()) {
                assignRoles(user, Set.of(UserRole.ROLE_USER));
            }
            return userMapper.userToUserResponseDto(user);
        } else {
            throw new AccessDeniedException("You do not have permission to access this endpoint");
        }
    }

    @Override
    public UserResponseDto findUser(long userId) {
        User user = userRepository.findWithRolesById(userId)
            .orElseThrow(() -> new UserNotFoundException("" + userId));

        if (hasPermissionToViewUser(user)) {
            return userMapper.userToUserResponseDto(user);
        }

        throw new AccessDeniedException("You do not have permission to access this endpoint");
    }

    @Override
    @Transactional
    public void updateUser(long userId, UserUpdateDto userUpdateDto) {
        User user = getUser(userId);

        if (hasPermissionToUpdateOrDeleteUser(user)) {
            userMapper.updateUserFromUserUpdateDto(userUpdateDto, user);
            if (userUpdateDto.currentPassword() != null && userUpdateDto.newPassword() != null) {
                updateUserPassword(userUpdateDto, user);
            }
            userRepository.save(user);
        }

        throw new AccessDeniedException("You do not have permission to access this endpoint");
    }

    @Override
    @Transactional
    public void deleteUser(long userId) {
        User user = getUser(userId);

        if (hasPermissionToUpdateOrDeleteUser(user)) {
            user.setTimeOfRemoval(LocalDateTime.now(ZoneOffset.UTC));
            userRepository.save(user);
        }

        throw new AccessDeniedException("You do not have permission to access this endpoint");
    }

    @Override
    @Transactional
    public void assignRoles(long userId, UserRolesUpdateDto userRolesUpdateDto) {
        User user = getUser(userId);
        List<Role> roles = roleService.findRolesByIds(userRolesUpdateDto.roleIds());
        user.setRoles(new HashSet<>(roles));
    }

    @Override
    public Page<UserBaseInfoDto> findUsersByFilterDto(Pageable pageable, UserFilterDto userFilterDto) {
        Specification<User> userSpec = parseFilterDtoToSpecification(userFilterDto);
        return findUsersBySpecification(userSpec, pageable);
    }

    @Override
    public User getUser(long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("" + userId));
    }

    @Override
    public Set<RoleBaseInfoDto> findAssignedRoles(long userId) {
        return userRepository.findWithRolesById(userId)
            .orElseThrow(() -> new UserNotFoundException("" + userId))
            .getRoles().stream()
            .map(roleMapper::roleToRoleBaseInfoDto)
            .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void restoreUser(long userId) {
        User user = getUser(userId);

        if (hasPermissionToRestore(user)) {
            user.setTimeOfRemoval(null);
            userRepository.save(user);
        }

        throw new AccessDeniedException("You do not have permission to access this endpoint");
    }

    private void setHashedPassword(User user, String rawPassword) {
        String passwordHash = passwordEncoder.encode(rawPassword);
        user.setPassword(passwordHash);
    }

    private void updateUserPassword(UserUpdateDto userUpdateDto, User user) {
        String currentEncodedPassword = user.getPassword();
        if (!passwordEncoder.matches(userUpdateDto.currentPassword(), currentEncodedPassword)) {
            throw new InvalidPasswordException();
        }

        String newEncodedPassword = passwordEncoder.encode(userUpdateDto.newPassword());
        user.setPassword(newEncodedPassword);
    }

    private void assignRoles(User user, Set<UserRole> userRoles) {
        Set<Role> roles = userRoles.stream()
            .map(userRole -> roleService.findRoleByName(userRole.name()))
            .collect(Collectors.toSet());
        user.setRoles(roles);
    }

    private Specification<User> parseFilterDtoToSpecification(UserFilterDto userFilterDto) {
        Specification<User> spec = Specification
            .where((root, query, cb) -> cb.conjunction());

        if (userFilterDto == null) {
            return spec;
        }

        if (userFilterDto.keyword() != null) {
            String keyword = userFilterDto.keyword();
            spec = spec.and(UserSpecifications.loginLike(userFilterDto.keyword())
                .or(UserSpecifications.emailLike(userFilterDto.keyword()))
            );
        }
        if (userFilterDto.dateOfBirthAfter() != null) {
            spec = spec.and(UserSpecifications.dateOfBirthOnOrAfter(userFilterDto.dateOfBirthAfter()));
        }
        if (userFilterDto.dateOfBirthBefore() != null) {
            spec = spec.and(UserSpecifications.dateOfBirthOnOrBefore(userFilterDto.dateOfBirthBefore()));
        }
        if (userFilterDto.deleted() != null) {
            spec = spec.and(UserSpecifications.isDeleted(userFilterDto.deleted()));
        }
        if (SecurityUtils.userHasRole(UserRole.ROLE_ADMIN.name())) {
            if (userFilterDto.roleIds() != null) {
                spec = spec.and(UserSpecifications.withRoleIdIn(userFilterDto.roleIds()));
            }
        } else if (SecurityUtils.userHasRole(UserRole.ROLE_MANAGER.name())) {
            spec = spec.and(UserSpecifications.withRoleIdIn(Set.of(roleService
                .findRoleByName(UserRole.ROLE_USER.name()).getId())));
        }
        return spec;
    }

    private Page<UserBaseInfoDto> findUsersBySpecification(Specification<User> specification, Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(specification, pageable);
        return usersPage.map(userMapper::userToUserBaseInfoDto);
    }

    private boolean hasPermissionToViewUser(User user) {
        if (SecurityUtils.userHasRole(UserRole.ROLE_ADMIN.name())) {
            return true;
        }

        if (user.getRoles().isEmpty()) {
            return false;
        }

        if (SecurityUtils.userHasId(user.getId())) {
            return true;
        }

        if (SecurityUtils.userHasRole(UserRole.ROLE_MANAGER.name())) {
            return user.getRoles().stream()
                .allMatch(role -> role.getName().equals(UserRole.ROLE_USER.name()));
        }

        return false;
    }

    private boolean hasPermissionToUpdateOrDeleteUser(User user) {
        if (SecurityUtils.userHasRole(UserRole.ROLE_ADMIN.name())) {
            return true;
        }

        if (SecurityUtils.userHasId(user.getId())) {
            return true;
        }

        if (SecurityUtils.userHasRole(UserRole.ROLE_MANAGER.name())) {
            return user.getRoles().stream()
                .allMatch(role -> role.getName().equals(UserRole.ROLE_USER.name()));
        }

        return false;
    }

    private boolean hasPermissionToRestore(User user) {
        if (SecurityUtils.userHasRole(UserRole.ROLE_ADMIN.name())) {
            return true;
        }

        if (SecurityUtils.userHasRole(UserRole.ROLE_MANAGER.name())) {
            return user.getRoles().stream()
                .allMatch(role -> role.getName().equals(UserRole.ROLE_USER.name()));
        }

        return false;
    }

    //private Map<String, List<String>> validateUserUpdateDto(User user, UserUpdateDto userUpdateDto, Locale locale) {
    //    Set<ConstraintViolation<UserUpdateDto>> constraintsViolations = validator.validate(userUpdateDto);
    //    Map<String, List<String>> constraintsViolationsMap = new HashMap<>();
    //    constraintsViolations.stream()
    //            .forEach(constraintViolation ->
    //            constraintsViolationsMap.computeIfAbsent(constraintViolation.getPropertyPath().toString(),
    //                    k -> new ArrayList<>()).add(constraintViolation.getMessage()));
    //
    //    if (!user.getLogin().equals(userUpdateDto.getLogin()) && userRepository.existsByLogin(userUpdateDto.getLogin())) {
    //        constraintsViolationsMap.computeIfAbsent("login",
    //                k -> new ArrayList<>()).add(messageSource.getMessage("accounts.users.edit.errors.login_not_unique", null, locale));
    //    }
    //    if (!user.getEmail().equals(userUpdateDto.getEmail()) && userRepository.existsByEmailIgnoreCase(userUpdateDto.getEmail())) {
    //        constraintsViolationsMap.computeIfAbsent("email",
    //                k -> new ArrayList<>()).add(messageSource.getMessage("accounts.users.edit.errors.email_not_unique", null, locale));
    //    }
    //    return constraintsViolationsMap;
    //}
}
