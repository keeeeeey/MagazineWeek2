package com.sparta.magazine_week2.controller;

import com.sparta.magazine_week2.dto.*;
import com.sparta.magazine_week2.entity.Post;
import com.sparta.magazine_week2.repository.PostRepository;
import com.sparta.magazine_week2.security.UserDetailsImpl;
import com.sparta.magazine_week2.service.PostService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    private final PostRepository postRepository;
    private final PostService postService;

    public PostController(PostRepository postRepository, PostService postService) {
        this.postRepository = postRepository;
        this.postService = postService;
    }

    @PostMapping("/api/post") //게시물 등록
    public UserResponseDto createpost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserResponseDto responseDto = new UserResponseDto();
        Post post = new Post(requestDto);
        String username = userDetails.getUsername();
        if (username == null) {
            responseDto.setResult(false);
            responseDto.setMsg("로그인 후 등록이 가능합니다.");
            return responseDto;
        }
        postRepository.save(post);
        responseDto.setResult(true);
        responseDto.setMsg("success");

        return responseDto;
    }

    //좋아요 다만들면 리턴 좋아요도 같이 해주기
    @GetMapping("/api/post") //게시물 조회
    public PostResponseDto getPost(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        PostResponseDto responseDto = new PostResponseDto();
        String username = userDetails.getUsername();
        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc();
        responseDto.setPosts(posts);
        responseDto.setUsername(username);
        return responseDto;
    }

    //게시물 수정
    @PutMapping("/api/post") //게시물 수정
    public UserResponseDto putPost(@RequestBody PostUpdateRequestDto updateRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserResponseDto responseDto = new UserResponseDto();
        String username = userDetails.getUsername();
        String username2 = updateRequestDto.getUsername();
        if (!username.equals(username2)) {
            responseDto.setResult(false);
            responseDto.setMsg("작성자만 수정이 가능합니다.");
            return responseDto;
        }
        return postService.update(updateRequestDto);
    }

    //게시물 삭제
    @DeleteMapping("/api/post") // 게시물 삭제
    public UserResponseDto deletePost(@RequestBody PostRemoveRequestDto removeRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        UserResponseDto responseDto = new UserResponseDto();
        String username = userDetails.getUsername();
        String username2 = removeRequestDto.getUsername();
        if (!username.equals(username2)) {
            responseDto.setResult(false);
            responseDto.setMsg("작성자만 삭제가 가능합니다.");
            return responseDto;
        }
        postRepository.deleteById(removeRequestDto.getPostId());
        responseDto.setResult(true);
        responseDto.setMsg("success");
        return responseDto;
    }

}
