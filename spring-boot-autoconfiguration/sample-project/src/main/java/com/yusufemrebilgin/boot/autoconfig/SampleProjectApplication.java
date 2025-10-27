package com.yusufemrebilgin.boot.autoconfig;

import com.yusufemrebilgin.boot.jps.core.JsonPlaceHolderClient;
import com.yusufemrebilgin.boot.jps.core.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
public class SampleProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleProjectApplication.class, args);
    }

    @RestController
    @RequestMapping("/users")
    static class UserController {

        private final JsonPlaceHolderClient jsonPlaceHolderClient;

        UserController(JsonPlaceHolderClient jsonPlaceHolderClient) {
            this.jsonPlaceHolderClient = jsonPlaceHolderClient;
        }

        @GetMapping
        List<User> getAllUsers() {
            return jsonPlaceHolderClient.listAllUsers();
        }

    }

}
