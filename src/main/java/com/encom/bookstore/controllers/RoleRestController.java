package com.encom.bookstore.controllers;

import com.encom.bookstore.dto.RoleBaseInfoDto;
import com.encom.bookstore.dto.RoleDto;
import com.encom.bookstore.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts/roles")
public class RoleRestController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<List<RoleBaseInfoDto>> getAllRoles() {
        List<RoleBaseInfoDto> rolesBaseInfo = roleService.findAllRoles();
        return ResponseEntity.ok(rolesBaseInfo);
    }

    @GetMapping("/{roleId:\\d+}")
    public ResponseEntity<RoleDto> getRole(@PathVariable("roleId") long roleId) {
        RoleDto roleDto = roleService.findRole(roleId);
        return ResponseEntity.ok(roleDto);
    }
}
