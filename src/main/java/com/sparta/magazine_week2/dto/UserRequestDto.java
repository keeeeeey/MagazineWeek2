package com.sparta.magazine_week2.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {

    private String username; //이메일아이디
    private String password; //비밀번호
    private String passwordCheck; //비밀번호
    private String nickName; //닉네임
    private boolean admin; //권한
    private String adminToken = ""; //관리자 토큰
}
