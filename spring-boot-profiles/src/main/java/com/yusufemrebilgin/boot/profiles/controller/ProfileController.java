package com.yusufemrebilgin.boot.profiles.controller;

import com.yusufemrebilgin.boot.profiles.config.ApplicationProperties;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/profiles")
public class ProfileController {

    private final Environment environment;
    private final ApplicationProperties properties;

    public ProfileController(Environment environment, ApplicationProperties properties) {
        this.environment = environment;
        this.properties = properties;
    }

    @GetMapping("/active")
    public Map<String, Object> getActiveProfile() {
        Map<String, Object> map = new HashMap<>();
        map.put("activeProfiles", environment.getActiveProfiles());
        map.put("applicationName", properties.name());
        map.put("applicationDescription", properties.description());
        return map;
    }

}
