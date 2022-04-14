package com.sparta.magazine_week2.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NO_AUTHENTICATION_ERROR(401, "401-1", "로그인 후 사용가능합니다.");

    private int statusCode;
    private final String errorCode;
    private final String message;

}
