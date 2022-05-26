package com.epam.esm.exception;

public class CertificateNotFoundException extends RuntimeException {
    public static final String ERROR_CODE = "01";
    private static final String CERTIFICATE_NOT_FOUND_MSG = "Certificate with id %d was not found";
    private final long id;

    public Object[] getArgs() {
        return new Object[]{id};
    }

    public CertificateNotFoundException(long id) {
        super(String.format(CERTIFICATE_NOT_FOUND_MSG, id));
        this.id = id;
    }

}
