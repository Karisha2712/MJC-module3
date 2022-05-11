package com.epam.esm.exception;

public class PageNotFoundException extends RuntimeException {
    public static final String ERROR_CODE = "06";
    private static final String PAGE_NOT_FOUND_MSG = "Page %d was not found. Total page number is %d";

    public PageNotFoundException(int currentPage, int totalPageNumber) {
        super(String.format(PAGE_NOT_FOUND_MSG, currentPage, totalPageNumber));
    }
}
