package com.encom.bookstore.security;

import com.encom.bookstore.model.User;
import com.encom.bookstore.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findWithRolesByLoginAndTimeOfRemovalNull(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));

        return new UserPrincipal(user);
    }
}
