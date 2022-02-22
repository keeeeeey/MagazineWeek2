package com.sparta.magazine_week2.controller;

import com.sparta.magazine_week2.dto.LikeRequestDto;
import com.sparta.magazine_week2.dto.UserResponseDto;
import com.sparta.magazine_week2.service.LikeService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    //게시글 좋아요
    @PostMapping("/api/like")
    public UserResponseDto clicklike(@RequestBody LikeRequestDto likeRequestDto, HttpServletRequest request){
        //세션 존재 확인
        HttpSession session = request.getSession();
        UserResponseDto returnDto = new UserResponseDto();
        if (session.getAttribute("responseDto") == null){
            returnDto.setResult(false);
            returnDto.setMsg("로그인을 해주세요");
            return returnDto;
        }
        return likeService.pluslike(likeRequestDto);
    }

    @DeleteMapping("/api/like")
    public UserResponseDto deletelike(@RequestBody LikeRequestDto likeRequestDto){
        UserResponseDto returnDto = new UserResponseDto();


        return likeService.dellike(likeRequestDto);
    }

}
