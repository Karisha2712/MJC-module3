package com.epam.esm.exception;

public class UserNotFoundException extends ResourceNotFoundException {
    private static final String ERROR_CODE = "07";
    private static final String USER_NOT_FOUND_MSG = "User with id %d was not found";
    private final long id;

    public UserNotFoundException(long id) {
        super(String.format(USER_NOT_FOUND_MSG, id), ERROR_CODE);
        this.id = id;
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{id};
    }
}
