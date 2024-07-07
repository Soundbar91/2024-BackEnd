package com.example.demo.controller.dto.request;

import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @NotNull(message = "이메일은 필수로 입력해야 합니다.") String email,
        @NotNull(message = "비밀번호는 필수로 입력해야 합니다.") String password
) {
}
