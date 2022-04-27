package com.sparta.magazine_week2.service;

import com.sparta.magazine_week2.dto.request.LoginRequestDto;
import com.sparta.magazine_week2.dto.request.UserRequestDto;
import com.sparta.magazine_week2.dto.response.LoginResponseDto.CommonLoginResponseDto;
import com.sparta.magazine_week2.entity.User;
import com.sparta.magazine_week2.exception.ErrorCode;
import com.sparta.magazine_week2.exception.ErrorCustomException;
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
                .orElseThrow(() -> new ErrorCustomException(ErrorCode.ALREADY_USERNAME_ERROR));

        String password = requestDto.getPassword();
        String passwordCheck = requestDto.getPasswordCheck();

        if (!password.equals(passwordCheck)) {
            throw new ErrorCustomException(ErrorCode.NO_MATCH_PASSWORD_ERROR);
        }

        String nickname = requestDto.getNickName();
        User findUserByNickname = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new ErrorCustomException(ErrorCode.ALREADY_NICKNAME_ERROR));

        // 패스워드 암호화
        String bcryptpassword = passwordEncoder.encode(password);

        User user = User.builder()
                .username(username)
                .password(bcryptpassword)
                .nickname(nickname)
                .build();

        userRepository.save(user);
        return user.getId();
    }

    //로그인
    @Transactional
    public CommonLoginResponseDto login(LoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new ErrorCustomException(ErrorCode.NO_USER_ERROR));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new ErrorCustomException(ErrorCode.NO_MATCH_PASSWORD_ERROR);
        }

        String accessToken = jwtTokenProvider.createToken(user.getUsername());

        return CommonLoginResponseDto.builder()
                .user_id(user.getId())
                .nickname(user.getNickname())
                .access_token(accessToken)
                .build();
    }
}
