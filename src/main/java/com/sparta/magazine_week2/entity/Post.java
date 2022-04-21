package com.sparta.magazine_week2.entity;

import com.sparta.magazine_week2.dto.request.PostRequestDto;
import com.sparta.magazine_week2.dto.request.PostRequestDto.PostUpdate;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends Timestamped {
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents; //내용

    @Column(columnDefinition = "BIGINT default 0")
    private int likeCount; //좋아요

    @Column(nullable = false)
    private String nickname; //닉네임

    @Enumerated(EnumType.STRING)
    private PostTypeEnum type; //타입

    @Builder
    public Post(final String title, final String contents, final int likeCount,
                final String nickname, final String type) {
        this.title = title;
        this.contents = contents;
        this.likeCount = likeCount;
        this.nickname = nickname;
        this.type = PostTypeEnum.valueOf(type);
    }

    public void update(PostUpdate requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
        this.type = PostTypeEnum.valueOf(requestDto.getType());
    }

    public void pluslike() {
        this.likeCount = likeCount + 1;
    }

    public void minuslike() {
        this.likeCount = likeCount - 1;
    }
}
