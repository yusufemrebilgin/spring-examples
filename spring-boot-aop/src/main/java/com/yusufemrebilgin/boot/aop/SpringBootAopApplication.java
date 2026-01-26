package com.yusufemrebilgin.boot.aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class SpringBootAopApplication {

    static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(SpringBootAopApplication.class, args);
        Arrays.stream(ctx.getBeanDefinitionNames()).forEach(IO::println);
    }

}
