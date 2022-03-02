package com.sparta.magazine_week2.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private boolean result;
    private String msg;

    public boolean getResult() {
        return result;
    }
}
