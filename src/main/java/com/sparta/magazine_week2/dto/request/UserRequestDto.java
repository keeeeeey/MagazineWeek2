package com.sparta.magazine_week2.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
public class UserRequestDto {

    @NotEmpty(message = "사용할 아이디를 입력해주세요.")
    private String username; //이메일아이디

    @NotEmpty(message = "사용할 비밀번호를 입력해주세요.")
    private String password; //비밀번호

    @NotEmpty(message = "비밀번호가 일치하지 않습니다.")
    private String passwordCheck; //비밀번호

    @NotEmpty(message = "사용할 닉네임을 입력해주세요.")
    private String nickName; //닉네임
}
