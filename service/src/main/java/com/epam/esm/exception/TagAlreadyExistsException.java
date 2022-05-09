package com.epam.esm.exception;

public class TagAlreadyExistsException extends RuntimeException {
    public static final String ERROR_CODE = "05";
    private static final String TAG_ALREADY_EXISTS_MSG = "Tag %s has already exists";

    public TagAlreadyExistsException(String name) {
        super(String.format(TAG_ALREADY_EXISTS_MSG, name));
    }
}
