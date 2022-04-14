package com.sparta.magazine_week2.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {
    private Long account_id;
    private String nickname;
    private String access_token;

    @Builder
    public LoginResponseDto(final Long account_id, final String nickname, final String access_token) {
        this.account_id = account_id;
        this.nickname = nickname;
        this.access_token = access_token;
    }
}
