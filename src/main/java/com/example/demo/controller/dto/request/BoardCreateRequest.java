package com.example.demo.controller.dto.request;

import com.example.demo.domain.Board;
import jakarta.validation.constraints.NotNull;

public record BoardCreateRequest(
    @NotNull(message = "게시판 이름은 필수로 입력해야 합니다.") String name
) {
    public Board toEntity() {
        return new Board(name);
    }
}
