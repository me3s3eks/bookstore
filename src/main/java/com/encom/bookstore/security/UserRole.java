package com.encom.bookstore.security;

public enum UserRole {

    ROLE_ADMIN,
    ROLE_MANAGER,
    ROLE_USER;

    private static final String rolePrefix = "ROLE_";

    public String getRoleNameWithoutPrefix() {
        String roleName = this.name();
        return roleName.substring(rolePrefix.length());
    }
}
