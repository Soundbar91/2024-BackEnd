package com.example.demo.controller.dto.request;

import com.example.demo.domain.Board;

public record BoardCreateRequest(
    String name
) {
    public Board toEntity() {
        return Board.builder()
                .name(this.name).build();
    }
}
