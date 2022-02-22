package com.sparta.magazine_week2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private boolean result;
    private String msg;
    private String username;
    private String nickName;

    public boolean getResult() {
        return result;
    }
}