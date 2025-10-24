package com.yusufemrebilgin.boot.profiles.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public record ApplicationProperties(
        String name,
        String description
) {}
