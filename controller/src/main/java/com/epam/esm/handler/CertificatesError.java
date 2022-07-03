package com.epam.esm.handler;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CertificatesError {
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
