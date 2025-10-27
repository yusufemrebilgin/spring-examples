package com.yusufemrebilgin.boot.jps.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jsonplaceholder.client")
public record JsonPlaceHolderClientProperties(boolean enabled, String baseUrl) {

}
