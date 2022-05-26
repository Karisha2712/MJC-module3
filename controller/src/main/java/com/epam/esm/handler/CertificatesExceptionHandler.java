package com.epam.esm.handler;

import com.epam.esm.exception.*;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Locale;

@RestControllerAdvice
public class CertificatesExceptionHandler {
    private final MessageSource messageSource;

    @Autowired
    public CertificatesExceptionHandler(@Qualifier("errorMessageSource") MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(TagNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CertificatesError handleTagNotFoundException(TagNotFoundException e) {
        String code = HttpStatus.NOT_FOUND.value() + TagNotFoundException.ERROR_CODE;
        String message = messageSource.getMessage(code, e.getArgs(), Locale.ENGLISH);
        return new CertificatesError(message, HttpStatus.NOT_FOUND, TagNotFoundException.ERROR_CODE);
    }

    @ExceptionHandler(CertificateNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CertificatesError handleCertificateNotFoundException(CertificateNotFoundException e) {
        String code = HttpStatus.NOT_FOUND.value() + CertificateNotFoundException.ERROR_CODE;
        String message = messageSource.getMessage(code, e.getArgs(), Locale.ENGLISH);
        return new CertificatesError(message, HttpStatus.NOT_FOUND, CertificateNotFoundException.ERROR_CODE);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CertificatesError handleUserNotFoundException(UserNotFoundException e) {
        String code = HttpStatus.NOT_FOUND.value() + UserNotFoundException.ERROR_CODE;
        String message = messageSource.getMessage(code, e.getArgs(), Locale.ENGLISH);
        return new CertificatesError(message, HttpStatus.NOT_FOUND, UserNotFoundException.ERROR_CODE);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CertificatesError handleOrderNotFoundException(OrderNotFoundException e) {
        String code = HttpStatus.NOT_FOUND.value() + OrderNotFoundException.ERROR_CODE;
        String message = messageSource.getMessage(code, e.getArgs(), Locale.ENGLISH);
        return new CertificatesError(message, HttpStatus.NOT_FOUND, OrderNotFoundException.ERROR_CODE);
    }

    @ExceptionHandler(PageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CertificatesError handlePageNotFoundException(PageNotFoundException e) {
        String code = HttpStatus.NOT_FOUND.value() + PageNotFoundException.ERROR_CODE;
        String message = messageSource.getMessage(code, e.getArgs(), Locale.ENGLISH);
        return new CertificatesError(message, HttpStatus.NOT_FOUND, PageNotFoundException.ERROR_CODE);
    }

    @ExceptionHandler(TagAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CertificatesError handleTagAlreadyExistsException(TagAlreadyExistsException e) {
        String code = HttpStatus.NOT_FOUND.value() + TagAlreadyExistsException.ERROR_CODE;
        String message = messageSource.getMessage(code, e.getArgs(), Locale.ENGLISH);
        return new CertificatesError(message, HttpStatus.NOT_FOUND, TagAlreadyExistsException.ERROR_CODE);
    }

    @ExceptionHandler(InvalidAttributeValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CertificatesError handleInvalidAttributeValueException(InvalidAttributeValueException e) {
        return new CertificatesError(e.getMessage(), HttpStatus.BAD_REQUEST, InvalidAttributeValueException.ERROR_CODE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CertificatesError handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = messageSource
                .getMessage("400-invalidArgument", null, Locale.ENGLISH);
        return new CertificatesError(message,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public CertificatesError handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String method = e.getMethod();
        String message = messageSource
                .getMessage("400-notAllowedMethod", new Object[]{method}, Locale.ENGLISH);
        return new CertificatesError(message, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CertificatesError handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        String message = messageSource
                .getMessage("400-invalidArgument", null, Locale.ENGLISH);
        return new CertificatesError(message, HttpStatus.BAD_REQUEST);
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
