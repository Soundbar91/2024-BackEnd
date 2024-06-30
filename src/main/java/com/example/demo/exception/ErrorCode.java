package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    ARTICLE_NOT_FOUND(404, HttpStatus.NOT_FOUND, "Article not found"),
    BOARD_NOT_FOUND(404, HttpStatus.NOT_FOUND, "Board not found"),
    MEMBER_NOT_FOUND(404, HttpStatus.NOT_FOUND, "Member not found"),

    EMAIL_EXISTS(409, HttpStatus.CONFLICT, "Email already exists"),

    FK_NOT_EXISTS(400, HttpStatus.BAD_REQUEST, "FK is not exists"),

    MEMBER_REFERENCE(409, HttpStatus.CONFLICT, "Member reference already exists"),
    BOARD_REFERENCE(409, HttpStatus.CONFLICT, "Board reference already exists"),

    UNKNOWN_EXCEPTION(500, HttpStatus.INTERNAL_SERVER_ERROR, "Unknown Exception");

    private final int code;
    private final HttpStatus status;
    private final String message;

    ErrorCode(int code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

}
