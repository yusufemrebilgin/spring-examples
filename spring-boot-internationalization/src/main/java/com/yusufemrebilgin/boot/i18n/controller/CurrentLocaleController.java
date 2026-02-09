package com.yusufemrebilgin.boot.i18n.controller;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/current-locale")
public class CurrentLocaleController {

    @GetMapping
    public Map<String, String> getCurrentLocaleInfo() {
        Map<String, String> localeMap = new HashMap<>();
        Locale currentLocale = LocaleContextHolder.getLocale();
        localeMap.put("locale", currentLocale.toString());
        localeMap.put("language", currentLocale.getLanguage());
        localeMap.put("displayLanguage", currentLocale.getDisplayLanguage());
        localeMap.put("country", currentLocale.getCountry());
        localeMap.put("displayCountry", currentLocale.getDisplayCountry());
        return localeMap;
    }

}
