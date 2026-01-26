package com.yusufemrebilgin.boot.aop.guide;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
// This annotation is usually not required for Spring Boot applications,
// because Spring Boot enables proxy-based AOP automatically via
// org.springframework.boot.autoconfigure.aop.AopAutoConfiguration
// when spring-boot-starter-aop is on the classpath.
//
// This annotation is needed when; AOP auto-configuration is disabled or
// custom proxy settings are required (e.g., proxyTargetClass = true)
@EnableAspectJAutoProxy
public class ApplicationConfiguration {

    @Bean
    public NotVeryUsefulAspect notVeryUsefulAspect() {
        NotVeryUsefulAspect aspect = new NotVeryUsefulAspect();
        // Configure properties of the aspect here
        return aspect;
    }

}
