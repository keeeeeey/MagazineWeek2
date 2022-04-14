package com.sparta.magazine_week2.dto.request;

import com.sparta.magazine_week2.entity.PostTypeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDto {
    private String contents;
    private String nickName;
    private int likeCount;
    private String image;
    private PostTypeEnum type;
}
