package com.sparta.magazine_week2.service;

import com.sparta.magazine_week2.dto.request.LoginRequestDto;
import com.sparta.magazine_week2.dto.request.UserRequestDto;
import com.sparta.magazine_week2.dto.response.LoginResponseDto;
import com.sparta.magazine_week2.entity.User;
import com.sparta.magazine_week2.repository.UserRepository;
import com.sparta.magazine_week2.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    //회원가입 확인
    @Transactional
    public Long createUser(UserRequestDto requestDto) {
        // 회원 ID 중복 확인
        String username = requestDto.getUsername();
        User findUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("중복된 아이디입니다."));

        String password = requestDto.getPassword();
        String passwordCheck = requestDto.getPasswordCheck();

        if (!password.equals(passwordCheck)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 패스워드 암호화
        String bcryptpassword = passwordEncoder.encode(password);
        String nickName = requestDto.getNickName();

        User user = User.builder()
                .username(username)
                .password(bcryptpassword)
                .nickname(nickName)
                .build();

        userRepository.save(user);
        return user.getId();
    }

    //로그인
    @Transactional
    public LoginResponseDto login(LoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 아이디 입니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        String accessToken = jwtTokenProvider.createToken(user.getUsername());

        return LoginResponseDto.builder()
                .account_id(user.getId())
                .nickname(user.getNickname())
                .access_token(accessToken)
                .build();
    }
}
