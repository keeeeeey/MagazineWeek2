package com.sparta.magazine_week2.entity;

import com.sparta.magazine_week2.dto.UserRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Post> post = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<LikeNumber> likeNumber = new ArrayList<>();

    public User(String username, String password, String nickName) {
        this.username = username;
        this.password = password;
        this.nickName = nickName;
    }

}