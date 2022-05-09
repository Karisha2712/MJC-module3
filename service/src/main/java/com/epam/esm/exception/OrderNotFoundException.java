package com.epam.esm.exception;

public class OrderNotFoundException extends RuntimeException {
    public static final String ERROR_CODE = "04";
    private static final String ORDER_NOT_FOUND_MSG = "Order with id %d was not found";

    public OrderNotFoundException(long id) {
        super(String.format(ORDER_NOT_FOUND_MSG, id));
    }
}
