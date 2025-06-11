package com.encom.bookstore.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SecurityUtils {

    public static boolean isUserAuthenticated() {
        Authentication token = getAuthenticationFromContext();
        return isUserAuthenticated(token);
    }

    public static boolean isUserAuthenticated(Authentication token) {
        return token != null
            && token.isAuthenticated()
            && !(token instanceof AnonymousAuthenticationToken);
    }

    public static boolean userHasRole(String role) {
        if (!isUserAuthenticated()) {
            return false;
        }

        Authentication token = getAuthenticationFromContext();

        return token.getAuthorities().stream()
            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(role));
    }

    private static Authentication getAuthenticationFromContext() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
