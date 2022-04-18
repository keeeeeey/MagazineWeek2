package com.sparta.magazine_week2.controller;

import com.sparta.magazine_week2.dto.Success;
import com.sparta.magazine_week2.dto.request.PostRequestDto;
import com.sparta.magazine_week2.security.UserDetailsImpl;
import com.sparta.magazine_week2.service.PostCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostCommentController {

    private final PostCommentService postCommentService;

    @PostMapping("/api/comment/{postId}")
    public ResponseEntity<Success> createComment(@RequestBody PostRequestDto.PostComment requestDto,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                 @PathVariable Long postId) {
        return new ResponseEntity<>(new Success("댓글 작성",
                postCommentService.createComment(requestDto, userDetails, postId)), HttpStatus.OK);
    }

    @PatchMapping("/api/comment/{commentId}")
    public ResponseEntity<Success> updateComment(@RequestBody PostRequestDto.PostComment requestDto,
                                                 @AuthenticationPrincipal UserDetailsImpl userDetails,
                                                 @PathVariable Long commentId) {
        return new ResponseEntity<>(new Success("댓글 수정",
                postCommentService.updateComment(requestDto, userDetails, commentId)), HttpStatus.OK);
    }

    @DeleteMapping("/api/comment/{commentId}")
    public ResponseEntity<Success> deleteComment(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                 @PathVariable Long commentId) {
        postCommentService.deleteComment(userDetails, commentId);
        return new ResponseEntity<>(new Success("댓글 삭제", ""), HttpStatus.OK);
    }
}
