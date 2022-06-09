package com.epam.esm.entity;

import lombok.Getter;

public enum UserRole {
    USER(1),
    ADMIN(2);

    @Getter
    private final int roleId;

    UserRole(int roleId) {
        this.roleId = roleId;
    }
}
