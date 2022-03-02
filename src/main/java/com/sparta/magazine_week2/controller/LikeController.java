package com.sparta.magazine_week2.controller;

import com.sparta.magazine_week2.dto.request.LikeRequestDto;
import com.sparta.magazine_week2.dto.response.UserResponseDto;
import com.sparta.magazine_week2.security.UserDetailsImpl;
import com.sparta.magazine_week2.service.LikeService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {
    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    //게시글 좋아요
    @PostMapping("/api/like")
    public UserResponseDto likeNotLike(@RequestBody LikeRequestDto likeRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return likeService.likeNotLike(likeRequestDto, userDetails);
    }

}
