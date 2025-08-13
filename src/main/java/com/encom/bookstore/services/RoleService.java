package com.encom.bookstore.services;

import com.encom.bookstore.dto.RoleBaseInfoDto;
import com.encom.bookstore.dto.RoleDto;
import com.encom.bookstore.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {

    List<RoleBaseInfoDto> findAllRoles();

    RoleDto findRole(long roleId);

    Role getRole(long roleId);

    Role findRoleByName(String roleName);

    List<Role> findRolesByIds(Set<Long> roleIds);
}
