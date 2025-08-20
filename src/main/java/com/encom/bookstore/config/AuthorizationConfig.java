package com.encom.bookstore.config;

import com.encom.bookstore.security.UserRole;
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
            c.requestMatchers(HttpMethod.POST, "/catalogue/authors/**")
                .hasRole(UserRole.ROLE_MANAGER.getRoleNameWithoutPrefix());
            c.requestMatchers(HttpMethod.GET, "/catalogue/authors/**")
                .permitAll();
            c.requestMatchers(HttpMethod.PUT, "/catalogue/authors/**")
                .hasRole(UserRole.ROLE_MANAGER.getRoleNameWithoutPrefix());
            c.requestMatchers(HttpMethod.DELETE, "/catalogue/authors/**")
                .hasRole(UserRole.ROLE_MANAGER.getRoleNameWithoutPrefix());
        });

        //Setting authorization for book categories' endpoints
        http.authorizeHttpRequests(c -> {
            c.requestMatchers(HttpMethod.POST, "/catalogue/book-categories/**")
                .hasRole(UserRole.ROLE_MANAGER.getRoleNameWithoutPrefix());
            c.requestMatchers(HttpMethod.GET, "/catalogue/book-categories/**")
                .permitAll();
            c.requestMatchers(HttpMethod.PUT, "/catalogue/book-categories/**")
                .hasRole(UserRole.ROLE_MANAGER.getRoleNameWithoutPrefix());
            c.requestMatchers(HttpMethod.DELETE, "/catalogue/book-categories/**")
                .hasRole(UserRole.ROLE_MANAGER.getRoleNameWithoutPrefix());
        });

        //Setting authorization for publishers' endpoints
        http.authorizeHttpRequests(c -> {
            c.requestMatchers(HttpMethod.POST, "/catalogue/publishers/**")
                .hasRole(UserRole.ROLE_MANAGER.getRoleNameWithoutPrefix());
            c.requestMatchers(HttpMethod.GET, "/catalogue/publishers/**")
                .permitAll();
            c.requestMatchers(HttpMethod.PUT, "/catalogue/publishers/**")
                .hasRole(UserRole.ROLE_MANAGER.getRoleNameWithoutPrefix());
            c.requestMatchers(HttpMethod.DELETE, "/catalogue/publishers/**")
                .hasRole(UserRole.ROLE_MANAGER.getRoleNameWithoutPrefix());
        });

        //Setting authorization for book variants' endpoints
        http.authorizeHttpRequests(c -> {
            c.requestMatchers(HttpMethod.POST, "/catalogue/books/{bookId}/variants")
                .hasRole(UserRole.ROLE_MANAGER.getRoleNameWithoutPrefix());
            c.requestMatchers(HttpMethod.GET, "/catalogue/books/{bookId}/variants")
                .permitAll();
            c.requestMatchers(HttpMethod.GET, "/catalogue/books/{bookId}/variants/{bookType}")
                .permitAll();
            c.requestMatchers(HttpMethod.PUT, "/catalogue/books/{bookId}/variants/{bookType}")
                .hasRole(UserRole.ROLE_MANAGER.getRoleNameWithoutPrefix());
        });

        //Setting authorization for books' endpoints
        http.authorizeHttpRequests(c -> {
            c.requestMatchers(HttpMethod.POST, "/catalogue/books/**")
                .hasRole(UserRole.ROLE_MANAGER.getRoleNameWithoutPrefix());
            c.requestMatchers(HttpMethod.GET, "/catalogue/books/**")
                .permitAll();
            c.requestMatchers(HttpMethod.PUT, "/catalogue/books/**")
                .hasRole(UserRole.ROLE_MANAGER.getRoleNameWithoutPrefix());
            c.requestMatchers(HttpMethod.DELETE, "/catalogue/books/**")
                .hasRole(UserRole.ROLE_MANAGER.getRoleNameWithoutPrefix());
        });

        //Setting authorization for users' endpoints
        http.authorizeHttpRequests(c -> {
            c.requestMatchers(HttpMethod.POST, "/accounts/users/restore")
                .hasAnyRole(UserRole.ROLE_ADMIN.getRoleNameWithoutPrefix(),
                    UserRole.ROLE_MANAGER.getRoleNameWithoutPrefix());
            c.requestMatchers(HttpMethod.POST, "/accounts/users")
                .permitAll();
            c.requestMatchers(HttpMethod.GET, "/accounts/users")
                .hasAnyRole(UserRole.ROLE_ADMIN.getRoleNameWithoutPrefix(),
                    UserRole.ROLE_MANAGER.getRoleNameWithoutPrefix());
            c.requestMatchers(HttpMethod.GET, "/accounts/users/{userId}")
                .authenticated();
            c.requestMatchers(HttpMethod.GET, "/accounts/users/{userId}/roles")
                .hasRole(UserRole.ROLE_ADMIN.getRoleNameWithoutPrefix());
            c.requestMatchers(HttpMethod.PUT, "/accounts/users/{userId}")
                .authenticated();
            c.requestMatchers(HttpMethod.PUT, "/accounts/users/{userId}/roles")
                .hasRole(UserRole.ROLE_ADMIN.getRoleNameWithoutPrefix());
            c.requestMatchers(HttpMethod.DELETE, "/accounts/users/{userId}")
                .authenticated();
        });

        //Setting authorization for delivery address's endpoints
        http.authorizeHttpRequests(c -> {
            c.requestMatchers(HttpMethod.POST, "/accounts/users/delivery-addresses")
                .authenticated();
            c.requestMatchers(HttpMethod.GET, "/accounts/users/delivery-addresses")
                .authenticated();
            c.requestMatchers(HttpMethod.PUT, "/accounts/users/delivery-addresses/{addressId}")
                .authenticated();
            c.requestMatchers(HttpMethod.DELETE, "/accounts/users/delivery-addresses/{addressId}")
                .authenticated();
        });

        http.authorizeHttpRequests(c -> c.anyRequest().permitAll());

        return http.build();
    }
}
