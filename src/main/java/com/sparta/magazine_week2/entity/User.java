package com.sparta.magazine_week2.entity;

import com.sparta.magazine_week2.dto.LoginRequestDto;
import com.sparta.magazine_week2.dto.UserRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username; //이메일아이디

    @Column(nullable = false)
    private String password; //비밀번호

    @Column(nullable = false, unique = true)
    private String nickName; //닉네임

    public User(UserRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.nickName = requestDto.getNickName();
    }

    public User(LoginRequestDto loginDto){
        this.username = loginDto.getUsername();
        this.password = loginDto.getPassword();
    }
}