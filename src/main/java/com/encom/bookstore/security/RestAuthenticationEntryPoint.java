package com.encom.bookstore.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class RestAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    private final MessageSource messageSource;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        String responseBody = "";
        Locale locale = request.getLocale();
        if (authException instanceof UsernameNotFoundException) {
            String localizedErrorMessage = messageSource.getMessage("errors.error.401.user_not_found",
                new Object[] {authException.getMessage()}, "errors.error.401.default", locale);
            responseBody = "{\"error\": \"" + localizedErrorMessage + "\"}";
        } else if (authException instanceof BadCredentialsException) {
            String localizedErrorMessage = messageSource.getMessage("errors.error.401.invalid_password",
                new Object[] {authException.getMessage()}, "errors.error.401.default", locale);
            responseBody = "{\"error\": \"" + localizedErrorMessage + "\"}";

        }
        response.getWriter().write(responseBody);
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("bookstore");
        super.afterPropertiesSet();
    }
}
