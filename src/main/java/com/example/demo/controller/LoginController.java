package com.example.demo.controller;

import com.example.demo.controller.dto.request.LoginRequest;
import com.example.demo.domain.Member;
import com.example.demo.service.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public String login(
            @Valid @ModelAttribute LoginRequest loginRequest,
            HttpServletResponse response
    ) {
        Member member = loginService.login(loginRequest);
        if (member == null) return "redirect:/login";

        Cookie cookie = new Cookie("memberId", String.valueOf(member.getId()));
        cookie.setHttpOnly(true);
        cookie.setSecure(true);

        response.addCookie(cookie);
        return "redirect:/main";
    }
}
