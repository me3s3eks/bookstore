package com.encom.bookstore.services.impl;

import com.encom.bookstore.dto.RoleBaseInfoDto;
import com.encom.bookstore.dto.RoleDto;
import com.encom.bookstore.exceptions.EntityNotFoundException;
import com.encom.bookstore.mappers.RoleMapper;
import com.encom.bookstore.model.Role;
import com.encom.bookstore.repositories.RoleRepository;
import com.encom.bookstore.services.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefaultRoleService implements RoleService {

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    @Override
    public List<RoleBaseInfoDto> findAllRoles() {
        return roleRepository.findAll().stream()
            .map(roleMapper::roleToRoleBaseInfoDto)
            .collect(Collectors.toList());
    }

    @Override
    public RoleDto findRole(long roleId) {
        Role role = getRole(roleId);
        return roleMapper.roleToRoleDto(role);
    }

    @Override
    public Role getRole(long roleId) {
        return roleRepository.findById(roleId)
            .orElseThrow(() -> new EntityNotFoundException("Role", Set.of(roleId)));
    }

    @Override
    public Role findRoleByName(String roleName) {
        return roleRepository.findByName(roleName)
            .orElseThrow(() -> new EntityNotFoundException("Role", Set.of(roleName)));
    }

    @Override
    public List<Role> findRolesByIds(Set<Long> roleIds) {
        return roleRepository.findAllById(roleIds);
    }
}
