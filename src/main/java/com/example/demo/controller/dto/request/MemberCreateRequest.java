package com.example.demo.controller.dto.request;

import com.example.demo.domain.Member;
import jakarta.validation.constraints.NotNull;

public record MemberCreateRequest(
    @NotNull(message = "회원 이름은 필수로 입력해야 합니다.") String name,
    @NotNull(message = "이메일은 필수로 입력해야 합니다.") String email,
    @NotNull(message = "비밀번호는 필수로 입력해야 합니다.") String password
) {
    public Member toEntity() {
        return Member.builder()
                .name(this.name)
                .email(this.email)
                .password(this.password).build();
    }
}
