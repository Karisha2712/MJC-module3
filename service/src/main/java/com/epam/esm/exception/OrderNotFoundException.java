package com.epam.esm.exception;

public class OrderNotFoundException extends ResourceNotFoundException {
    private static final String ERROR_CODE = "04";
    private static final String ORDER_NOT_FOUND_MSG = "Order with id %d was not found";
    private final long id;

    public OrderNotFoundException(long id) {
        super(String.format(ORDER_NOT_FOUND_MSG, id), ERROR_CODE);
        this.id = id;
    }

    public Object[] getArgs() {
        return new Object[]{id};
    }
}
