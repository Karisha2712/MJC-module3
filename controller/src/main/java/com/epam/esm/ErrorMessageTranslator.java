package com.epam.esm;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
@PropertySource("classpath:/webapp.properties")
public class ErrorMessageTranslator {
    @Bean
    @Qualifier("errorMessageSource")
    public MessageSource errorMessageSource(
            @Value("${app.errorFile}") String resourceBundleBaseName,
            @Value("${app.defaultEncoding}") String defaultEncoding) {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename(resourceBundleBaseName);
        source.setDefaultEncoding(defaultEncoding);
        return source;
    }
}
