package com.encom.bookstore.controllers;

import com.encom.bookstore.dto.RoleBaseInfoDto;
import com.encom.bookstore.dto.UserBaseInfoDto;
import com.encom.bookstore.dto.UserFilterDto;
import com.encom.bookstore.dto.UserRequestDto;
import com.encom.bookstore.dto.UserResponseDto;
import com.encom.bookstore.dto.UserRolesUpdateDto;
import com.encom.bookstore.dto.UserUpdateDto;
import com.encom.bookstore.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts/users")
public class UserRestController {

    private final UserService userService;

    //~exclude negative id pattern (delete dash)
    @PostMapping
    public ResponseEntity<UserResponseDto> createUserAccount(@Valid @RequestBody UserRequestDto userRequestDto,
                                                             BindingResult bindingResult,
                                                             UriComponentsBuilder uriBuilder)
        throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException bindException) {
                throw bindException;
            } else {
                throw new BindException(bindingResult);
            }
        }
        UserResponseDto userResponseDto = userService.createUser(userRequestDto);
        return ResponseEntity
            .created(uriBuilder
                .replacePath("/accounts/users/{userId}")
                .build(userResponseDto.id()))
            .body(userResponseDto);
    }

    @GetMapping
    public ResponseEntity<Page<UserBaseInfoDto>> getUsers(
        Pageable pageable,
        @Valid @RequestBody(required = false) UserFilterDto userFilterDto,
        BindingResult bindingResult) throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException bindException) {
                throw bindException;
            } else {
                throw new BindException(bindingResult);
            }
        }
        Page<UserBaseInfoDto> userPage = userService.findUsersByFilterDto(pageable, userFilterDto);
        return ResponseEntity.ok(userPage);
    }

    //~exclude negative id pattern (delete dash)
    @GetMapping("/{userId:[-\\d]+}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable("userId") long userId) {
        UserResponseDto userResponseDto = userService.findUser(userId);
        return ResponseEntity.ok(userResponseDto);
    }

    //~exclude negative id pattern (delete dash)
    @PutMapping("/{userId:[-\\d]+}")
    public ResponseEntity<Void> updateUser(@PathVariable("userId") long userId,
                                           @Valid @RequestBody UserUpdateDto userUpdateDto,
                                           BindingResult bindingResult)
        throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException bindException) {
                throw bindException;
            } else {
                throw new BindException(bindingResult);
            }
        }
        userService.updateUser(userId, userUpdateDto);
        return ResponseEntity.noContent().build();
    }

    //~exclude negative id pattern (delete dash)
    @GetMapping("/{userId:[-\\d]+}/roles")
    public ResponseEntity<Set<RoleBaseInfoDto>> getAssignedRoles(@PathVariable("userId") long userId) {
        Set<RoleBaseInfoDto> assignedRoles = userService.findAssignedRoles(userId);
        return ResponseEntity.ok(assignedRoles);
    }

    //~exclude negative id pattern (delete dash)
    @PutMapping("/{userId:[-\\d]+}/roles")
    public ResponseEntity<Void> assignRoles(@PathVariable("userId") long userId,
                                            @Valid @RequestBody UserRolesUpdateDto userRolesUpdateDto,
                                            BindingResult bindingResult)
        throws BindException {
        if (bindingResult.hasErrors()) {
            if (bindingResult instanceof BindException bindException) {
                throw bindException;
            } else {
                throw new BindException(bindingResult);
            }
        }
        userService.assignRoles(userId, userRolesUpdateDto);
        return ResponseEntity.noContent().build();
    }

    //~exclude negative id pattern (delete dash)
    @DeleteMapping("/{userId:[-\\d]+}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    //~exclude negative id pattern (delete dash)
    @PostMapping("/{userId:[-\\d]+}/restore")
    public ResponseEntity<Void> restoreUser(@PathVariable("userId") long userId) {
        userService.restoreUser(userId);
        return ResponseEntity.noContent().build();
    }
}
