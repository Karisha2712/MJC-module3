package com.epam.esm.entity;

import lombok.Getter;

public enum UserRole {
    GUEST(1),
    USER(2),
    ADMIN(3);

    @Getter
    private final int roleId;

    UserRole(int roleId) {
        this.roleId = roleId;
    }
}
