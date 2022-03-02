package com.sparta.magazine_week2.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeRequestDto {
    private Long postId;
    private Long userId;
}
