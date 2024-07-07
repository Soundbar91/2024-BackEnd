package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PageController {

    // 클라이언트에서 JavaScript를 이용해 API를 호출함.
    @GetMapping("/main")
    public String getBoardsPage() {
        return "boardList";
    }

    // 클라이언트에서 JavaScript를 이용해 API를 호출함.
    @GetMapping("/posts")
    public String getArticlesPage() {
        return "articleList";
    }

    @GetMapping("/posts/detail")
    public String getArticleDetailPage() {
        return "article";
    }

    @GetMapping("/register")
    public String getRegisterPage() {
        return "register";
    }
}
