package com.encom.bookstore.services;

import com.encom.bookstore.dto.RoleBaseInfoDto;
import com.encom.bookstore.dto.UserBaseInfoDto;
import com.encom.bookstore.dto.UserFilterDto;
import com.encom.bookstore.dto.UserRequestDto;
import com.encom.bookstore.dto.UserResponseDto;
import com.encom.bookstore.dto.UserRolesUpdateDto;
import com.encom.bookstore.dto.UserUpdateDto;
import com.encom.bookstore.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface UserService {

    UserResponseDto createUser(UserRequestDto userRequestDto);

    UserResponseDto findUser(long userId);

    void updateUser(long userId, UserUpdateDto userUpdateDto);

    void deleteUser(long userId);

    void assignRoles(long userId, UserRolesUpdateDto userRolesUpdateDto);

    Page<UserBaseInfoDto> findUsersByFilterDto(Pageable pageable, UserFilterDto userFilterDto);

    User getUser(long userId);

    Set<RoleBaseInfoDto> findAssignedRoles(long userId);

    void restoreUser(long userId);
}
