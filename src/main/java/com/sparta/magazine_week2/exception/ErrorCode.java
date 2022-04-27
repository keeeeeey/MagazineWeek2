package com.sparta.magazine_week2.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NO_AUTHENTICATION_ERROR(401, "401-1", "로그인 후 사용가능합니다."),
    NO_AUTHORIZATION_ERROR(403,"A005","접근 권한이 없습니다."),
    NO_USER_ERROR(404, "A001","해당 유저를 찾을 수 없습니다."),
    ALREADY_USERNAME_ERROR(400, "V001","이미 사용중인 아이디입니다."),
    ALREADY_NICKNAME_ERROR(400, "V002","이미 사용중인 닉네임입니다."),
    NO_MATCH_PASSWORD_ERROR(400, "V003","비밀번호가 일치하지 않습니다."),
    NONEXISTENT_ERROR(404,"AP001","존재하지 않는 게시글 및 작업물입니다");

    private int statusCode;
    private final String errorCode;
    private final String message;

}
