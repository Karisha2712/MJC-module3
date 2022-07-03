package com.epam.esm.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;

public enum UserRole implements GrantedAuthority {
    ROLE_USER(1),
    ROLE_ADMIN(2);

    @Getter
    private final int roleId;

    UserRole(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public String getAuthority() {
        return this.name();
    }

    public static UserRole getRoleById(int roleId) {
        return Arrays.stream(UserRole.values())
                .filter(role -> role.roleId == roleId)
                .findFirst()
                .orElse(ROLE_USER);
    }
}
