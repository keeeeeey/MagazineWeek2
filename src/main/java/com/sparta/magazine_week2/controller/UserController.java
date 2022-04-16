package com.sparta.magazine_week2.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.magazine_week2.dto.Success;
import com.sparta.magazine_week2.dto.request.LoginRequestDto;
import com.sparta.magazine_week2.dto.request.UserRequestDto;
import com.sparta.magazine_week2.service.KakaoService;
import com.sparta.magazine_week2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final KakaoService kakaoService;

    //유저 회원가입
    @PostMapping("/user/register")
    public ResponseEntity<Success> createUser(@RequestBody UserRequestDto requestDto){
        return new ResponseEntity<>(new Success("회원가입",
                userService.createUser(requestDto)), HttpStatus.OK);
    }

    @PostMapping("/user/login")
    public ResponseEntity<Success> login(@RequestBody LoginRequestDto requestDto) {
        return new ResponseEntity<>(new Success("로그인", userService.login(requestDto)), HttpStatus.OK);
    }

    @GetMapping("/user/kakao/callback")
    public ResponseEntity<Success> login(@RequestParam("code") String code) throws JsonProcessingException {
        return new ResponseEntity<>(new Success("카카오 로그인", kakaoService.kakaoLogin(code)), HttpStatus.OK);
    }
}
