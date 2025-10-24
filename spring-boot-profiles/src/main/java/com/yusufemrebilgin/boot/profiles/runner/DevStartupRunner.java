package com.yusufemrebilgin.boot.profiles.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DevStartupRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        System.out.println(DevStartupRunner.class.getSimpleName() + " is started...");
    }

}