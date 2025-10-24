package com.yusufemrebilgin.boot.filters.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "filter.request-logging")
public record RequestLoggingFilterProperties(
        boolean enabled,
        int order,
        String name,
        List<String> urlPatterns
) {}
