package com.sparta.magazine_week2.dto;

import com.sparta.magazine_week2.entity.Post;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequestDto {

    private String contents; //내용
    private String nickName;
    private String username;
    private Long likeCount;
    private String image; //내용
    private String type;

    public PostRequestDto(Post post) {
        this.contents = post.getContents();
        this.nickName = post.getNickName();
        this.username = post.getUsername();
        this.likeCount = post.getLikeCount();
        this.image = post.getImage();
        this.type = post.getType();
    }
}
