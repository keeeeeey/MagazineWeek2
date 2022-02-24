package com.sparta.magazine_week2.entity;

import com.sparta.magazine_week2.dto.LikeRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class LikeNumber {
    @Id
    @Column(name = "like_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private Long userId;

    public LikeNumber(LikeRequestDto likeRequestDto){
        this.postId = likeRequestDto.getPostId();
        this.userId = likeRequestDto.getUserId();
    }

}