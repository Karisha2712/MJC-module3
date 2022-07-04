package com.epam.esm.exception;

public class TagNotFoundException extends ResourceNotFoundException {
    private static final String ERROR_CODE = "02";
    private static final String TAG_NOT_FOUND_MSG = "Tag with id %d was not found";
    private final long id;

    public TagNotFoundException(long id) {
        super(String.format(TAG_NOT_FOUND_MSG, id), ERROR_CODE);
        this.id = id;
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{id};
    }
}
