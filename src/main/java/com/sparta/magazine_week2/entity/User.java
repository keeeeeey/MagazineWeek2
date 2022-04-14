package com.sparta.magazine_week2.entity;

import com.sparta.magazine_week2.validator.UserValidator;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Builder
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

}