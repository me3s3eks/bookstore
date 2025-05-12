package com.encom.bookstore.mappers;

import com.encom.bookstore.dto.UserCreateDto;
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
    User userCreateDtoToUser(UserCreateDto userCreateDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromUserUpdateDto(UserUpdateDto userUpdateDto, @MappingTarget User user);
}
