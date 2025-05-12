package com.encom.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PasswordEncoderConfig {
    private static final String BCRYPT_ID = "bcrypt";
    private static final String NOOP_ID = "noop";

    @Bean
    public PasswordEncoder delegatingPasswordEncoder() {
        Map<String, PasswordEncoder> supportedEncoders = new HashMap<>();
        supportedEncoders.put(BCRYPT_ID, new BCryptPasswordEncoder(
            BCryptPasswordEncoder.BCryptVersion.$2B, 12));
        supportedEncoders.put(NOOP_ID, NoOpPasswordEncoder.getInstance());

        return new DelegatingPasswordEncoder(BCRYPT_ID, supportedEncoders);
    }
}
