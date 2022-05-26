package com.epam.esm.exception;

public class UserNotFoundException extends RuntimeException {
    public static final String ERROR_CODE = "07";
    private static final String USER_NOT_FOUND_MSG = "User with id %d was not found";
    private final long id;

    public Object[] getArgs() {
        return new Object[]{id};
    }

    public UserNotFoundException(long id) {
        super(String.format(USER_NOT_FOUND_MSG, id));
        this.id = id;
    }
}
