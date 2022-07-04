package com.epam.esm.security;

import com.epam.esm.security.filter.ExceptionHandlingFilter;
import com.epam.esm.security.filter.UserAuthenticationFilter;
import com.epam.esm.security.filter.UserAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SecurityExceptionHandler handler;
    private final ExceptionHandlingFilter exceptionHandlingFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.formLogin()
                .failureHandler(handler)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(handler)
                .authenticationEntryPoint(handler);
        http.addFilter(new UserAuthenticationFilter(authenticationManager()));
        http.addFilterAfter(new UserAuthorizationFilter(userDetailsService), UserAuthenticationFilter.class);
        http.addFilterBefore(exceptionHandlingFilter, CorsFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public UserAuthenticationFilter authenticationFilter(AuthenticationManager manager) {
        UserAuthenticationFilter authenticationFilter = new UserAuthenticationFilter(manager);
        authenticationFilter.setFilterProcessesUrl("/login");
        return authenticationFilter;
    }
}


