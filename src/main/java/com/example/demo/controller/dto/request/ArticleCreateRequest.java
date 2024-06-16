package com.example.demo.controller.dto.request;

import com.example.demo.exception.ApplicationException;
import com.example.demo.exception.ErrorCode;

public record ArticleCreateRequest(
    Long authorId,
    Long boardId,
    String title,
    String description
) {
    public ArticleCreateRequest {
        if (authorId == null || boardId == null || title == null || description == null) {
            throw new ApplicationException(ErrorCode.FIELD_NULL);
        }
        if (description.isBlank() || title.isBlank()) {
            throw new ApplicationException(ErrorCode.FIELD_NULL);
        }
    }
}
