package com.sparta.magazine_week2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    private String username; //이메일아이디
    private String password; //비밀번호
}
