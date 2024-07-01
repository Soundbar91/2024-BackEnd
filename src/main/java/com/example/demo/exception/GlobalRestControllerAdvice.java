package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalRestControllerAdvice {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ExceptionResponse> ApplicationLogicException(ApplicationException e) {
        ExceptionResponse response = new ExceptionResponse(e.getCode(), e.getHttpStatus(), e.getMessage());
        return new ResponseEntity<>(response, e.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> dtoValidation(MethodArgumentNotValidException e) {
        final int errorCode = 400;
        final HttpStatus httpStatus = BAD_REQUEST;

        List<String> list = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach((error)-> {
            String errorMessage = error.getDefaultMessage();
            list.add(errorMessage);
        });

        ExceptionResponse response = new ExceptionResponse(errorCode, httpStatus, list);
        return new ResponseEntity<>(response, httpStatus);
    }
}
