package com.epam.esm.exception;

public class PageNotFoundException extends ResourceNotFoundException {
    private static final String ERROR_CODE = "06";
    private static final String PAGE_NOT_FOUND_MSG = "Page %d was not found. Total page number is %d";
    private final int currentPage;
    private final int totalPageNumber;

    public PageNotFoundException(int currentPage, int totalPageNumber) {
        super(String.format(PAGE_NOT_FOUND_MSG, currentPage, totalPageNumber), ERROR_CODE);
        this.currentPage = currentPage;
        this.totalPageNumber = totalPageNumber;
    }

    @Override
    public Object[] getArgs() {
        return new Object[]{currentPage, totalPageNumber};
    }
}
