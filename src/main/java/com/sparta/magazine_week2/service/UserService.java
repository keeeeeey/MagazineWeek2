package com.sparta.magazine_week2.service;

import com.sparta.magazine_week2.dto.request.UserRequestDto;
import com.sparta.magazine_week2.dto.response.UserResponseDto;
import com.sparta.magazine_week2.entity.User;
import com.sparta.magazine_week2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //회원가입 확인
    @Transactional
    public UserResponseDto checkRegister(UserRequestDto requestDto) {
        UserResponseDto responseDto = new UserResponseDto();

        // 회원 ID 중복 확인
        String username = requestDto.getUsername();

        if (username == null) {
            throw new IllegalArgumentException("아이디를 입력해주세요.");
        }

        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 아이디입니다.");
        }

        String password = requestDto.getPassword();
        String passwordCheck = requestDto.getPasswordCheck();
        if (!password.equals(passwordCheck)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        // 패스워드 암호화
        String bcryptpassword = passwordEncoder.encode(password);
        String nickName = requestDto.getNickName();

//        User user = new User(username, bcryptpassword, nickName);
        User user = User.UserBuilder()
                .username(username)
                .password(bcryptpassword)
                .nickName(nickName)
                .build();

        userRepository.save(user);

        responseDto.setMsg("회원가입이 완료 되었습니다.");
        responseDto.setResult(true);
        return responseDto;
    }

}
