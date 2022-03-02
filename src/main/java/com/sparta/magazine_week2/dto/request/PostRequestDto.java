package com.sparta.magazine_week2.dto.request;

import com.sparta.magazine_week2.entity.PostTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    private String contents;
    private String nickName;
    private int likeCount;
    private String image;
    private PostTypeEnum type;
}
