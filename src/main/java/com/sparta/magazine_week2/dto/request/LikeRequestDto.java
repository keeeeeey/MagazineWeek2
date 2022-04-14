package com.sparta.magazine_week2.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeRequestDto {
    private Long postId;
    private Long userId;
}
