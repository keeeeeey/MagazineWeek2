package com.sparta.magazine_week2.controller;

import com.sparta.magazine_week2.dto.PostRemoveRequestDto;
import com.sparta.magazine_week2.dto.PostRequestDto;
import com.sparta.magazine_week2.dto.PostUpdateRequestDto;
import com.sparta.magazine_week2.dto.UserResponseDto;
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
    public UserResponseDto createpost(@RequestBody PostRequestDto requestDto) {
        UserResponseDto responseDto = new UserResponseDto();
        Post post = new Post(requestDto);
        postRepository.save(post);
        responseDto.setResult(true);
        responseDto.setMsg("success");

        return responseDto;
    }

    //좋아요 다만들면 리턴 좋아요도 같이 해주기
    @GetMapping("/api/post") //게시물 조회
    public List<Post> getPost(){
        return postRepository.findAllByOrderByModifiedAtDesc();
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
        }
        return postService.update(updateRequestDto);
    }

    //게시물 삭제
    @DeleteMapping("/api/post") // 게시물 삭제
    public UserResponseDto deletePost(@RequestBody PostRemoveRequestDto removeRequestDto, @AuthenticationPrincipal UserDetailsImpl userDtails){
        UserResponseDto responseDto = new UserResponseDto();
        postRepository.deleteById(removeRequestDto.getPostId());
        responseDto.setResult(true);
        responseDto.setMsg("success");
        return responseDto;
    }

}
