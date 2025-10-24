package com.yusufemrebilgin.boot.filters.config;

import com.yusufemrebilgin.boot.filters.filter.RequestLoggingFilter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(RequestLoggingFilterProperties.class)
public class RequestLoggingFilterConfig {

    private final RequestLoggingFilterProperties properties;

    public RequestLoggingFilterConfig(RequestLoggingFilterProperties properties) {
        this.properties = properties;
    }

    @Bean
    public FilterRegistrationBean<RequestLoggingFilter> filterFilterRegistrationBean(RequestLoggingFilter filter) {
        FilterRegistrationBean<RequestLoggingFilter> registrationBean = new FilterRegistrationBean<>(filter);
        if (!properties.enabled()) {
            registrationBean.setEnabled(false);
            return registrationBean;
        }

        registrationBean.setOrder(properties.order());
        registrationBean.setName(properties.name());
        registrationBean.setUrlPatterns(properties.urlPatterns());
        return registrationBean;
    }

}
