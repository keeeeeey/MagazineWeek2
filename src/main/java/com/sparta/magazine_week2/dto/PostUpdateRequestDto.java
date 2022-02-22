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
    private int likeCount;
    private String image; //내용
    private String type;

    public PostUpdateRequestDto(Post post){
        this.postId = post.getId();
        this.contents = post.getContents();
        this.nickName = post.getNickName();
        this.likeCount = post.getLikeCount();
        this.image = post.getImage();
        this.type = post.getType();
    }
}
