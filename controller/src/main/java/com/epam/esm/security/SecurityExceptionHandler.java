package com.epam.esm.security;

import com.epam.esm.handler.CertificatesError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Component
public class SecurityExceptionHandler implements AuthenticationFailureHandler, AccessDeniedHandler, AuthenticationEntryPoint{

    private final MessageSource messageSource;
    private final ObjectMapper mapper;

    @Autowired
    public SecurityExceptionHandler(@Qualifier("errorMessageSource") MessageSource messageSource,
                                    ObjectMapper mapper) {
        this.messageSource = messageSource;
        this.mapper = mapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        sendError(HttpStatus.FORBIDDEN, response);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        sendError(HttpStatus.UNAUTHORIZED, response);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        sendError(HttpStatus.UNAUTHORIZED, response);
    }

    public void sendError(HttpStatus status, HttpServletResponse response) throws IOException {
        String code = status.value() + "";
        String message = messageSource.getMessage(code, null, Locale.ENGLISH);
        CertificatesError certificatesError = new CertificatesError(message, status);
        response.setStatus(status.value());
        response.setContentType(APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getOutputStream(), certificatesError);
    }

}
