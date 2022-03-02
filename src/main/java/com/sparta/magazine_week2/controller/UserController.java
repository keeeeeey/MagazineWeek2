package com.sparta.magazine_week2.controller;

import com.sparta.magazine_week2.dto.request.LoginRequestDto;
import com.sparta.magazine_week2.dto.response.LoginResponseDto;
import com.sparta.magazine_week2.dto.request.UserRequestDto;
import com.sparta.magazine_week2.dto.response.UserResponseDto;
import com.sparta.magazine_week2.entity.User;
import com.sparta.magazine_week2.repository.UserRepository;
import com.sparta.magazine_week2.security.JwtTokenProvider;
import com.sparta.magazine_week2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserController(UserService userService, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/api/register") //유저 회원가입
    public UserResponseDto createuser(@RequestBody UserRequestDto requestDto){
        //회원가입 확인
        return userService.checkRegister(requestDto);
    }

    @PostMapping("/api/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 아이디 입니다."));
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        LoginResponseDto responseDto = new LoginResponseDto();
        String token = jwtTokenProvider.createToken(user.getUsername(), Long.toString(user.getId()), user.getNickName());
        responseDto.setResult(true);
        responseDto.setMsg("success");
        responseDto.setTokenname(token);
        return responseDto;
    }
}
