package com.sparta.magazine_week2.validator;

import org.springframework.stereotype.Component;

@Component
public class UserValidator {
    public static void validateUserInput(String username, String password, String nickName) {
        if (username == null || username == "") {
            throw new IllegalArgumentException("아이디를 입력해주세요.");
        }

        if (password == null || password == "") {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }

        if (nickName == null || nickName == "") {
            throw new IllegalArgumentException("닉네임을 입력해주세요.");
        }
    }
}
