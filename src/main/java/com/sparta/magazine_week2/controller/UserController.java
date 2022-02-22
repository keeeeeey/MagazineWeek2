package com.sparta.magazine_week2.controller;

import com.sparta.magazine_week2.dto.LoginRequestDto;
import com.sparta.magazine_week2.dto.LoginResponseDto;
import com.sparta.magazine_week2.dto.UserRequestDto;
import com.sparta.magazine_week2.dto.UserResponseDto;
import com.sparta.magazine_week2.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/register") //유저 회원가입
    public UserResponseDto createuser(@RequestBody UserRequestDto requestDto){
        //회원가입 확인
        return userService.checkRegister(requestDto);
    }

    @PostMapping("/api/login") //유저 로그인
    public LoginResponseDto checklogin(@RequestBody LoginRequestDto loginDto, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //로그인 확인
        LoginResponseDto responseDto = userService.checklogin(loginDto);

        if (responseDto.getResult()){
            HttpSession session = request.getSession();
            session.setAttribute("responseDto",responseDto);
            System.out.println(session.getAttribute("responseDto"));

        }
        return responseDto;
    }
}
