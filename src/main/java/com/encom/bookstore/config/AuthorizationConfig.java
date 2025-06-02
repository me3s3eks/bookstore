package com.encom.bookstore.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthorizationConfig {
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AuthenticationProvider authenticationProvider;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(c -> {
            c.authenticationEntryPoint(authenticationEntryPoint);
        });

        http.csrf(c -> c.disable());

        http.authenticationProvider(authenticationProvider);

        //Setting authorization for authors' endpoints
        http.authorizeHttpRequests(c -> {
            c.requestMatchers(HttpMethod.POST, "/catalogue/authors/**").hasRole("MANAGER");
            c.requestMatchers(HttpMethod.GET, "/catalogue/authors/**").permitAll();
            c.requestMatchers(HttpMethod.PATCH, "/catalogue/authors/**").hasRole("MANAGER");
            c.requestMatchers(HttpMethod.DELETE, "/catalogue/authors/**").hasRole("MANAGER");
        });

        //Setting authorization for book categories' endpoints
        http.authorizeHttpRequests(c -> {
            c.requestMatchers(HttpMethod.POST, "/catalogue/book-categories/**").hasRole("MANAGER");
            c.requestMatchers(HttpMethod.GET, "/catalogue/book-categories/**").permitAll();
            c.requestMatchers(HttpMethod.PATCH, "/catalogue/book-categories/**").hasRole("MANAGER");
            c.requestMatchers(HttpMethod.DELETE, "/catalogue/book-categories/**").hasRole("MANAGER");
        });

        //Setting authorization for publishers' endpoints
        http.authorizeHttpRequests(c -> {
            c.requestMatchers(HttpMethod.POST, "/catalogue/publishers/**").hasRole("MANAGER");
            c.requestMatchers(HttpMethod.GET, "/catalogue/publishers/**").permitAll();
            c.requestMatchers(HttpMethod.PUT, "/catalogue/publishers/**").hasRole("MANAGER");
            c.requestMatchers(HttpMethod.DELETE, "/catalogue/publishers/**").hasRole("MANAGER");
        });

        http.authorizeHttpRequests(c -> c.anyRequest().permitAll());

        return http.build();
    }
}
