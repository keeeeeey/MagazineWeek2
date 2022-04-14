package com.sparta.magazine_week2.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NO_AUTHENTICATION_ERROR(HttpStatus.BAD_REQUEST, "400_1", "중복폴더명이 이미 존재합니다.");

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;
}
