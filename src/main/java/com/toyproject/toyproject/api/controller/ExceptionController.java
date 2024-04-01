package com.toyproject.toyproject.api.controller;


import com.toyproject.toyproject.api.exception.HodologException;
import com.toyproject.toyproject.api.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(HodologException.class)
    public ResponseEntity<ErrorResponse> hodologException(HodologException e) {

        int statusCode = e.getStatusCode();

        ErrorResponse response = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

        return ResponseEntity.status(statusCode).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exception(Exception e) {

        log.error("예외발생", e);

//        int statusCode = e.getStatusCode();

        ErrorResponse response = ErrorResponse.builder()
                .code("500")
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(500).body(response);
    }
}
