package com.encom.bookstore.utils;

import com.encom.bookstore.security.UserPrincipal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;

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

    public static boolean userHasAnyRole(Set<String> roles) {
        if (!isUserAuthenticated()) {
            return false;
        }

        Authentication token = getAuthenticationFromContext();
        return token.getAuthorities().stream()
            .anyMatch(grantedAuthority ->
                roles.stream()
                    .anyMatch(role -> grantedAuthority.getAuthority().equals(role)));
    }

    public static boolean userHasId(long userId) {
        if (!isUserAuthenticated()) {
            return false;
        }

        Authentication token = getAuthenticationFromContext();
        UserPrincipal userPrincipal = (UserPrincipal) token.getPrincipal();
        return userId == userPrincipal.getId();
    }

    private static Authentication getAuthenticationFromContext() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
