package com.example.demo.controller.dto.request;

import com.example.demo.exception.ApplicationException;
import com.example.demo.exception.ErrorCode;

public record BoardCreateRequest(
    String name
) {
    public BoardCreateRequest {
        if (name == null || name.isBlank())
            throw new ApplicationException(ErrorCode.FIELD_NULL);
    }
}
