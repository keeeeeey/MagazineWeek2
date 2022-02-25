package com.sparta.magazine_week2.dto;

import com.sparta.magazine_week2.entity.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {

    private String contents;
    private String nickName;
    private String username;
    private int likeCount;
    private String image;
    private String type;
}
