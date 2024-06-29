package com.example.demo.exception;

import org.springframework.http.HttpStatus;

public record ExceptionResponse(int code, HttpStatus status, Object message) {
}
