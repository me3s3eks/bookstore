package com.encom.bookstore.mappers;

import com.encom.bookstore.dto.UserBaseInfoDto;
import com.encom.bookstore.dto.UserRequestDto;
import com.encom.bookstore.dto.UserResponseDto;
import com.encom.bookstore.dto.UserUpdateDto;
import com.encom.bookstore.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    User userRequestDtoToUser(UserRequestDto userRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateUserFromUserUpdateDto(UserUpdateDto userUpdateDto, @MappingTarget User user);

    UserResponseDto userToUserResponseDto(User user);

    UserBaseInfoDto userToUserBaseInfoDto(User user);
}
