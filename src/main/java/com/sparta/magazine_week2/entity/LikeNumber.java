package com.sparta.magazine_week2.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    @JoinColumn(name = "post_id", foreignKey = @ForeignKey(name = "FK_SUBJECT_POST"))
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_SUBJECT_USER"))
    private User user;

    public LikeNumber(Post post, User user){
        this.post = post;
        this.user = user;
    }

}