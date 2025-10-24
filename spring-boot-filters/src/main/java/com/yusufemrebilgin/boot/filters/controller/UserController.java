package com.yusufemrebilgin.boot.filters.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private static final Random RAND = new Random();

    public record User(UUID id, String username) {
        private static User random() {
            return new User(UUID.randomUUID(), UUID.randomUUID().toString().substring(0, 8));
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        var users = Stream.generate(User::random).limit(RAND.nextInt(10)).toList();
        return ResponseEntity.ok(users);
    }


}
