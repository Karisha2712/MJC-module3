package com.epam.esm.handler;

import com.epam.esm.exception.InvalidAttributeValueException;
import com.epam.esm.exception.OrderCanNotBeEmptyException;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.TagAlreadyExistsException;
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

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CertificatesError handleTagNotFoundException(ResourceNotFoundException e) {
        String errorCode = e.getErrorCode();
        String code = HttpStatus.NOT_FOUND.value() + errorCode;
        String message = messageSource.getMessage(code, e.getArgs(), Locale.ENGLISH);
        return new CertificatesError(message, HttpStatus.NOT_FOUND, errorCode);
    }

    @ExceptionHandler(TagAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CertificatesError handleTagAlreadyExistsException(TagAlreadyExistsException e) {
        String code = HttpStatus.NOT_FOUND.value() + TagAlreadyExistsException.ERROR_CODE;
        String message = messageSource.getMessage(code, e.getArgs(), Locale.ENGLISH);
        return new CertificatesError(message, HttpStatus.NOT_FOUND, TagAlreadyExistsException.ERROR_CODE);
    }

    @ExceptionHandler(OrderCanNotBeEmptyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CertificatesError handleOrderCanNotBeEmptyException(OrderCanNotBeEmptyException e) {
        String code = HttpStatus.BAD_REQUEST.value() + OrderCanNotBeEmptyException.ERROR_CODE;
        String message = messageSource.getMessage(code, null, Locale.ENGLISH);
        return new CertificatesError(message, HttpStatus.BAD_REQUEST, OrderCanNotBeEmptyException.ERROR_CODE);
    }

    @ExceptionHandler(InvalidAttributeValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CertificatesError handleInvalidAttributeValueException(InvalidAttributeValueException e) {
        String code = HttpStatus.BAD_REQUEST.value() + InvalidAttributeValueException.ERROR_CODE;
        String message = messageSource.getMessage(code, null, Locale.ENGLISH);
        return new CertificatesError(message, HttpStatus.BAD_REQUEST, OrderCanNotBeEmptyException.ERROR_CODE);
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
                .getMessage("400-invalidArgumentType", null, Locale.ENGLISH);
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
