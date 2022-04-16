package com.sparta.magazine_week2.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username; //이메일아이디

    @Column(nullable = false)
    private String password; //비밀번호

    @Column(nullable = false, unique = true)
    private String nickname; //닉네임

    private String profileImg;

    @Builder
    public User(final String username, final String password, final String nickname, final String profileImg) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.profileImg = profileImg;
    }

}