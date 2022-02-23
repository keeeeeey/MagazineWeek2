package com.sparta.magazine_week2.controller;

import com.sparta.magazine_week2.dto.UserRequestDto;
import com.sparta.magazine_week2.dto.UserResponseDto;
import com.sparta.magazine_week2.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.SchemaOutputResolver;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/register") //유저 회원가입
    public UserResponseDto createuser(UserRequestDto requestDto){
        //회원가입 확인
        return userService.checkRegister(requestDto);
    }
}
