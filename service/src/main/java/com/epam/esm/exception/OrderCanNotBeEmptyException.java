package com.epam.esm.exception;

public class OrderCanNotBeEmptyException extends RuntimeException {
    public static final String ERROR_CODE = "08";
    private static final String ORDER_CAN_NOT_BE_EMPTY = "Order should contain at least one certificate";

    public OrderCanNotBeEmptyException() {
        super(ORDER_CAN_NOT_BE_EMPTY);
    }
}
