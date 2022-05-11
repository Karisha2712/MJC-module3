package com.epam.esm.handler;

import com.epam.esm.exception.*;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class CertificatesExceptionHandler {

    @ExceptionHandler(TagNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CertificatesError handleTagNotFoundException(TagNotFoundException e) {
        return new CertificatesError(e.getMessage(), HttpStatus.NOT_FOUND, TagNotFoundException.ERROR_CODE);
    }

    @ExceptionHandler(CertificateNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CertificatesError handleCertificateNotFoundException(CertificateNotFoundException e) {
        return new CertificatesError(e.getMessage(), HttpStatus.NOT_FOUND, CertificateNotFoundException.ERROR_CODE);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CertificatesError handleUserNotFoundException(UserNotFoundException e) {
        return new CertificatesError(e.getMessage(), HttpStatus.NOT_FOUND, UserNotFoundException.ERROR_CODE);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CertificatesError handleOrderNotFoundException(OrderNotFoundException e) {
        return new CertificatesError(e.getMessage(), HttpStatus.NOT_FOUND, OrderNotFoundException.ERROR_CODE);
    }

    @ExceptionHandler(PageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CertificatesError handlePageNotFoundException(PageNotFoundException e) {
        return new CertificatesError(e.getMessage(), HttpStatus.NOT_FOUND, PageNotFoundException.ERROR_CODE);
    }

    @ExceptionHandler(TagAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CertificatesError handleTagAlreadyExistsException(TagAlreadyExistsException e) {
        return new CertificatesError(e.getMessage(), HttpStatus.NOT_FOUND, TagAlreadyExistsException.ERROR_CODE);
    }

    @ExceptionHandler(InvalidAttributeValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CertificatesError handleInvalidAttributeValueException(InvalidAttributeValueException e) {
        return new CertificatesError(e.getMessage(), HttpStatus.BAD_REQUEST, InvalidAttributeValueException.ERROR_CODE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CertificatesError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new CertificatesError("Argument is not valid" + e,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public CertificatesError handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String method = e.getMethod();
        return new CertificatesError(String.format("Method %s is not allowed", method), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CertificatesError handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return new CertificatesError("Argument type is not valid" + e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CertificatesError handleRuntimeException(RuntimeException e) {
        return new CertificatesError(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Data
    private static class CertificatesError {
        private String errorCode;
        private String errorMessage;

        public CertificatesError(String errorMessage, HttpStatus status, String errorCode) {
            this.errorCode = status.value() + errorCode;
            this.errorMessage = errorMessage;
        }

        public CertificatesError(String errorMessage, HttpStatus status) {
            this.errorCode = String.valueOf(status.value());
            this.errorMessage = errorMessage;
        }
    }
}
