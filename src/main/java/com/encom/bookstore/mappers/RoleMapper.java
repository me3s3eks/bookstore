package com.encom.bookstore.mappers;

import com.encom.bookstore.dto.RoleBaseInfoDto;
import com.encom.bookstore.dto.RoleDto;
import com.encom.bookstore.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    RoleDto roleToRoleDto(Role role);

    RoleBaseInfoDto roleToRoleBaseInfoDto(Role role);
}
