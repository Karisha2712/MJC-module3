package com.epam.esm.exception;

public class InvalidAttributeValueException extends RuntimeException {
    public static final String ERROR_CODE = "03";
    private static final String INVALID_ATTRIBUTE_MSG = "Attribute value is invalid";

    public InvalidAttributeValueException() {
        super(INVALID_ATTRIBUTE_MSG);
    }
}
