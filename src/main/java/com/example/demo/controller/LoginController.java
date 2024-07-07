package com.example.demo.controller;

import com.example.demo.controller.dto.request.LoginRequest;
import com.example.demo.domain.Member;
import com.example.demo.exception.ApplicationException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.service.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

import static com.example.demo.exception.ErrorCode.*;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(
            @Valid @RequestBody LoginRequest loginRequest,
            HttpServletResponse response
    ) {
        Member member = Optional.ofNullable(loginService.login(loginRequest))
                .orElseThrow(() -> new ApplicationException(MISMATCH_PASSWORD));

        Cookie cookie = new Cookie("memberId", String.valueOf(member.getId()));
        cookie.setHttpOnly(true);
        cookie.setSecure(true);

        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }
}
