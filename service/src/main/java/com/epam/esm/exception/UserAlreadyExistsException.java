package com.epam.esm.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public static final String ERROR_CODE = "10";
    private static final String USER_ALREADY_EXISTS_MSG = "User with such login has already exists";

    public UserAlreadyExistsException() {
        super(USER_ALREADY_EXISTS_MSG);
    }
}
