package com.yusufemrebilgin.boot.i18n.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @PostMapping
    public void registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        // ...
    }

    public record UserRegistrationRequest(

            @Email(message = "{validation.email}")
            String email,

            @NotBlank(message = "{validation.password.required}")
            @Size(message = "{validation.password.size}", min = 8, max = 64)
            String password

    ) {}

}
