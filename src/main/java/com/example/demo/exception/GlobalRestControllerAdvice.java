package com.example.demo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalRestControllerAdvice {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ExceptionResponse> exception(ApplicationException e) {
        ExceptionResponse response = new ExceptionResponse(e.getCode(), e.getHttpStatus(), e.getMessage());
        return new ResponseEntity<>(response, e.getHttpStatus());
    }

}
