package com.sparta.magazine_week2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AroundHubExceptionHandler {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<Object> ExceptionHandler(IllegalArgumentException e) {
        AroundHubException aroundHubException = new AroundHubException();
        aroundHubException.setHttpStatus(HttpStatus.BAD_REQUEST);
        aroundHubException.setMsg(e.getMessage());
        return new ResponseEntity(aroundHubException, HttpStatus.BAD_REQUEST);
    }
}
