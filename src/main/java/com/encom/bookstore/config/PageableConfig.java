package com.encom.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

@Configuration
public class PageableConfig {
    private final int PAGE_MAX_SIZE = 100;

    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer customize() {
        return resolver -> {
            resolver.setMaxPageSize(PAGE_MAX_SIZE);
        };
    }
}
