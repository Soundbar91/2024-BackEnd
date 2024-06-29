package com.example.demo.controller.dto.request;

import com.example.demo.domain.Member;

public record MemberCreateRequest(
    String name,
    String email,
    String password
) {
    public Member toEntity() {
        return Member.builder()
                .name(this.name)
                .email(this.email)
                .password(this.password).build();
    }
}
