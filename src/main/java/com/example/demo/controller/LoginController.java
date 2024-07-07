package com.example.demo.controller;

import com.example.demo.controller.dto.request.LoginRequest;
import com.example.demo.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @Valid @RequestBody LoginRequest loginRequest
    ) {
        return loginService.login(loginRequest) ?
                ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
