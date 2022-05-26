package com.epam.esm.exception;

public class TagNotFoundException extends RuntimeException {
    public static final String ERROR_CODE = "02";
    private static final String TAG_NOT_FOUND_MSG = "Tag with id %d was not found";
    private final long id;

    public Object[] getArgs() {
        return new Object[]{id};
    }

    public TagNotFoundException(long id) {
        super(String.format(TAG_NOT_FOUND_MSG, id));
        this.id = id;
    }
}
