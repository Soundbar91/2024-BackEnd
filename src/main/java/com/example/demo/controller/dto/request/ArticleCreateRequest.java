package com.example.demo.controller.dto.request;

import com.example.demo.domain.Article;
import com.example.demo.domain.Board;
import com.example.demo.domain.Member;

import java.time.LocalDateTime;

public record ArticleCreateRequest(
    Long authorId,
    Long boardId,
    String title,
    String description
) {
    public Article toEntity(Board board, Member member) {
        return Article.builder()
                .member(member)
                .board(board)
                .title(this.title)
                .content(this.description)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now()).build();
    }
}
