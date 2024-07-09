package com.example.demo.controller;

import com.example.demo.controller.dto.request.LoginRequest;
import com.example.demo.controller.dto.response.MemberResponse;
import com.example.demo.domain.Member;
import com.example.demo.exception.ApplicationException;
import com.example.demo.service.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<MemberResponse> login(
            @Valid @ModelAttribute LoginRequest loginRequest,
            HttpServletResponse response,
            HttpServletRequest request
    ) throws IOException {
        MemberResponse member = loginService.login(loginRequest);
        if (member == null) {
            response.sendRedirect(request.getContextPath());
            return ResponseEntity.badRequest().build();
        }

        Cookie cookie = new Cookie("memberId", String.valueOf(member.id()));
        cookie.setHttpOnly(true);
        cookie.setSecure(true);

        response.addCookie(cookie);
        response.sendRedirect(request.getContextPath() + "/main");
        return ResponseEntity.ok(member);
    }
}
