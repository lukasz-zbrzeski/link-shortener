package com.example.linkshortener.controller;

import com.example.linkshortener.exception.LinkNotFoundException;
import com.example.linkshortener.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LinkControllerAdvice {

    @ExceptionHandler(LinkNotFoundException.class)
    protected ResponseEntity<ErrorResponse> linkNotFoundHandler(LinkNotFoundException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

}
