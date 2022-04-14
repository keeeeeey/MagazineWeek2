package com.sparta.magazine_week2.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRequestDto {

    private String username; //이메일아이디
    private String password; //비밀번호
    private String passwordCheck; //비밀번호
    private String nickName; //닉네임
}
