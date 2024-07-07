package com.example.demo.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("POST".equals(request.getMethod()) && "/members".equals(request.getRequestURI())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return true;
        }

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("memberId")) {
                    response.sendRedirect(request.getContextPath() + "/main");
                    return true;
                }
            }
        }

        response.setStatus(SC_UNAUTHORIZED);
        response.sendRedirect(request.getContextPath() + "/login");
        return false;
    }
}
