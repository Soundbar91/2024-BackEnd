package com.example.demo.controller.dto.request;

import com.example.demo.domain.Article;
import com.example.demo.domain.Board;
import com.example.demo.domain.Member;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ArticleCreateRequest(
    @NotNull(message = "회원 아이디는 필수로 입력해야 합니다.") Long authorId,
    @NotNull(message = "게시판 아이디는 필수로 입력해야 합니다.") Long boardId,
    @NotNull(message = "제목은 필수로 입력해야 합니다.") String title,
    @NotNull(message = "게시물 내용은 필수로 입력해야 합니다.") String description
) {
    public Article toEntity(Board board, Member member) {
        return Article.builder()
                .member(member)
                .board(board)
                .createdAt(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now()).build();
    }
}
