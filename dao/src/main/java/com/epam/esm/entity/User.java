package com.epam.esm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class User extends AbstractEntity {
    private String login;
    private String password;
}
