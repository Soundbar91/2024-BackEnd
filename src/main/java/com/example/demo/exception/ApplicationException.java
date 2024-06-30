package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException {
    private final int code;
    private final HttpStatus httpStatus;
    private final String message;

    public ApplicationException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.httpStatus = errorCode.getStatus();
        this.message = errorCode.getMessage();
    }
}
