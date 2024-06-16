package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    ARTICLE_NOT_FOUND(404, HttpStatus.NOT_FOUND, "Article not found");

    private final int code;
    private final HttpStatus status;
    private final String message;

    ErrorCode(int code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
