package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    ARTICLE_NOT_FOUND(404, HttpStatus.NOT_FOUND, "Article not found"),
    BOARD_NOT_FOUND(404, HttpStatus.NOT_FOUND, "Board not found"),
    MEMBER_NOT_FOUND(404, HttpStatus.NOT_FOUND, "Member not found"),

    EMAIL_EXISTS(409, HttpStatus.CONFLICT, "Email already exists"),

    FK_BOARD_NOT_EXISTS(400, HttpStatus.BAD_REQUEST, "Board FK is not exists"),

    FIELD_NULL(400, HttpStatus.BAD_REQUEST, "Field is null or blank");

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
