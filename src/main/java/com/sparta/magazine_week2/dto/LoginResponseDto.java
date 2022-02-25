package com.sparta.magazine_week2.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private boolean result;
    private String msg;
    private String token;
}
