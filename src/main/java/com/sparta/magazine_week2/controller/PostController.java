package com.sparta.magazine_week2.controller;

import com.sparta.magazine_week2.dto.Success;
import com.sparta.magazine_week2.dto.request.PostRequestDto;
import com.sparta.magazine_week2.security.UserDetailsImpl;
import com.sparta.magazine_week2.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    //게시물 등록
    @PostMapping("/api/post")
    public ResponseEntity<Success> createpost(@RequestBody PostRequestDto requestDto,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return new ResponseEntity<>(new Success("게시글 작성",
                postService.createPost(requestDto, userDetails)), HttpStatus.OK);
    }

    //게시물 조회
    @GetMapping("/api/post")
    public ResponseEntity<Success> getPost() {
        return new ResponseEntity<>(new Success("게시글 조회",
                postService.getPost()), HttpStatus.OK);
    }

    //상세 게시글 조회
    @GetMapping("/api/post/{postId}")
    public ResponseEntity<Success> getPostDetail() {
        return null;
    }

    //게시물 수정
    @PatchMapping("/api/post/{postId}")
    public ResponseEntity<Success> putPost(@PathVariable Long postId,
                                           @RequestBody PostRequestDto requestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return new ResponseEntity<>(new Success("게시글 수정",
                postService.update(requestDto, postId, userDetails)), HttpStatus.OK);
    }

    //게시물 삭제
    @DeleteMapping("/api/post/{postId}")
    public ResponseEntity<Success> deletePost(@PathVariable Long postId,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        postService.deletePost(postId, userDetails);
        return new ResponseEntity<>(new Success("게시글 삭제", ""), HttpStatus.OK);
    }

}
