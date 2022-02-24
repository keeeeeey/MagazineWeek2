package com.sparta.magazine_week2.entity;

import com.sparta.magazine_week2.dto.PostRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Post extends Timestamped {
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String contents; //내용

    @Column
    @ColumnDefault("0")
    private Long likeCount; //좋아요

    @Column(nullable = false)
    private String nickName; //닉네임

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String image; //내용

    @Column(nullable = false)
    private String type; //내용

    public Post(PostRequestDto requestDto){
        this.contents = requestDto.getContents();
        this.nickName = requestDto.getNickName();
        this.username = requestDto.getUsername();
        this.likeCount = requestDto.getLikeCount();
        this.image = requestDto.getImage();
        this.type = requestDto.getType();
    }

    public void update(PostRequestDto requestDto) {
        this.contents = requestDto.getContents();
        this.nickName = requestDto.getNickName();
        this.username = requestDto.getUsername();
        this.likeCount = requestDto.getLikeCount();
        this.image = requestDto.getImage();
        this.type = requestDto.getType();
    }
}
