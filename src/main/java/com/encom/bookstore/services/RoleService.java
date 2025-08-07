package com.encom.bookstore.services;

import com.encom.bookstore.dto.RoleBaseInfoDto;
import com.encom.bookstore.dto.RoleDto;
import com.encom.bookstore.model.Role;

import java.util.List;

public interface RoleService {

    List<RoleBaseInfoDto> findAllRoles();

    RoleDto findRole(long roleId);

    Role getRole(long roleId);
}
