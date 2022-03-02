package com.sparta.magazine_week2.entity;

import com.sparta.magazine_week2.dto.request.PostRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Setter
@Getter
@Entity
@Builder
public class Post extends Timestamped {
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "post_id")
    private List<LikeNumber> likeNumber = new ArrayList<>();

    @Column(nullable = false)
    private String contents; //내용

    @Column
    private int likeCount; //좋아요

    @Column(nullable = false)
    private String nickName; //닉네임

    @Column(nullable = false)
    private String image; //내용

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PostTypeEnum type; //내용

    public void update(PostRequestDto requestDto) {
        this.contents = requestDto.getContents();
        this.nickName = requestDto.getNickName();
        this.likeCount = requestDto.getLikeCount();
        this.image = requestDto.getImage();
        this.type = requestDto.getType();
    }

    public void pluslike() {
        this.likeCount = likeCount + 1;
    }

    public void minuslike() {
        this.likeCount = likeCount - 1;
    }
}
