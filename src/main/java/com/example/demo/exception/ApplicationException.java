package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {
    private final int code;
    private final HttpStatus httpStatus;
    private final String message;

    public ApplicationException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.httpStatus = errorCode.getStatus();
        this.message = errorCode.getMessage();
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
