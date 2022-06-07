package com.epam.esm.exception;

public abstract class ResourceNotFoundException extends RuntimeException {
    private final String errorCode;

    protected ResourceNotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public abstract Object[] getArgs();

}
