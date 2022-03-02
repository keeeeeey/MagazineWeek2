package com.sparta.magazine_week2.entity;

import com.sparta.magazine_week2.validator.UserValidator;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
//@Setter
@Entity
//@Builder(builderMethodName = "UserBuilder")
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private List<LikeNumber> likeNumber = new ArrayList<>();

    @Column(nullable = false, unique = true)
    private String username; //이메일아이디

    @Column(nullable = false)
    private String password; //비밀번호

    @Column(nullable = false, unique = true)
    private String nickName; //닉네임

    @Builder(builderClassName = "UserBuilder", builderMethodName = "UserBuilder")
    public User(String username, String password, String nickName) {
        UserValidator.validateUserInput(username, password, nickName);

        this.username = username;
        this.password = password;
        this.nickName = nickName;
    }

}