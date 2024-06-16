package com.example.demo.controller.dto.request;

import com.example.demo.exception.ApplicationException;
import com.example.demo.exception.ErrorCode;

public record MemberCreateRequest(
    String name,
    String email,
    String password
) {
    public MemberCreateRequest {
        if (name == null || name.isBlank()) {
            throw new ApplicationException(ErrorCode.FIELD_NULL);
        }
        if (email == null || email.isBlank()) {
            throw new ApplicationException(ErrorCode.FIELD_NULL);
        }
        if (password == null || password.isBlank()) {
            throw new ApplicationException(ErrorCode.FIELD_NULL);
        }
    }
}
