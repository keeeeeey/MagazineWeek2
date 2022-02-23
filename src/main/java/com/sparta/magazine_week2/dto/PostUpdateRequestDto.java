package com.sparta.magazine_week2.dto;

import com.sparta.magazine_week2.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateRequestDto {
    private Long postId;
    private String contents; //내용
    private String nickName;
    private String username;
    private int likeCount;
    private String image; //내용
    private String type;
}
