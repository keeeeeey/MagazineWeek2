package com.sparta.magazine_week2.controller;

import com.sparta.magazine_week2.dto.Success;
import com.sparta.magazine_week2.dto.request.CommonDto;
import com.sparta.magazine_week2.dto.request.CommonDto.Id;
import com.sparta.magazine_week2.exception.ErrorCode;
import com.sparta.magazine_week2.exception.ErrorCustomException;
import com.sparta.magazine_week2.security.UserDetailsImpl;
import com.sparta.magazine_week2.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    //게시글 좋아요/ 좋아요 취소
    @PostMapping("/api/like")
    public ResponseEntity<Success> likeNotLike(@RequestBody Id postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            likeService.likeNotLike(postId.getId(), userDetails.getUser().getId());
            return new ResponseEntity<>(new Success("게시글 좋아요/좋아요 취소", ""), HttpStatus.OK);
        }
        throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
    }

}
