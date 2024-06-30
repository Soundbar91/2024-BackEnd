package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {
    ARTICLE_NOT_FOUND(404, NOT_FOUND, "Article not found"),
    BOARD_NOT_FOUND(404, NOT_FOUND, "Board not found"),
    MEMBER_NOT_FOUND(404, NOT_FOUND, "Member not found"),

    EMAIL_ALREADY_EXISTS(409, CONFLICT, "Email already exists"),
    BOARD_ALREADY_EXISTS(409, CONFLICT, "Board already exists"),

    MEMBER_REFERENCE_EXISTS(400, BAD_REQUEST, "Member reference already exists"),
    BOARD_REFERENCE_EXISTS(400, BAD_REQUEST, "Board reference already exists"),

    UNKNOWN_EXCEPTION(500, INTERNAL_SERVER_ERROR, "Unknown Exception");

    private final int code;
    private final HttpStatus status;
    private final String message;

    ErrorCode(int code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

}
