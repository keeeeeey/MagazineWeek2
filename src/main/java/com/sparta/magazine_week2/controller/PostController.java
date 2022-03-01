package com.sparta.magazine_week2.controller;

import com.sparta.magazine_week2.dto.*;
import com.sparta.magazine_week2.entity.LikeNumber;
import com.sparta.magazine_week2.entity.Post;
import com.sparta.magazine_week2.repository.LikeRepository;
import com.sparta.magazine_week2.repository.PostRepository;
import com.sparta.magazine_week2.security.UserDetailsImpl;
import com.sparta.magazine_week2.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostRepository postRepository;
    private final PostService postService;
    private final LikeRepository likeRepository;

    @PostMapping("/api/post") //게시물 등록
    public UserResponseDto createpost(@RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UserResponseDto responseDto = new UserResponseDto();
        Post post = new Post(requestDto);
        if (userDetails == null) {
            throw new IllegalArgumentException("로그인 후 등록이 가능합니다.");
        }
        postRepository.save(post);
        responseDto.setResult(true);
        responseDto.setMsg("success");

        return responseDto;
    }

    //좋아요 다만들면 리턴 좋아요도 같이 해주기
    @GetMapping("/api/post") //게시물 조회
    public PostResponseDto getPost(@RequestBody PostGetRequestDto requestDto) {
        PostResponseDto responseDto = new PostResponseDto();
        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc();
        List<LikeNumber> likePosts = likeRepository.findAllByUserId(requestDto.getUserId());
        responseDto.setMyLike(likePosts);
        responseDto.setTotal(posts);

        return responseDto;
    }

    //게시물 수정
    @PutMapping("/api/post/{postId}") //게시물 수정
    public UserResponseDto putPost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String nickname = userDetails.getUser().getNickName();
        String nickname2 = requestDto.getNickName();

        if (!nickname.equals(nickname2)) {
            throw new IllegalArgumentException("작성자만 수정이 가능합니다.");
        }
        return postService.update(requestDto, postId);
    }

    //게시물 삭제
    @DeleteMapping("/api/post/{postId}") // 게시물 삭제
    public UserResponseDto deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        String nickname = userDetails.getUser().getNickName();
        Optional<Post> post = postRepository.findById(postId);

        if (!post.isPresent()) {
            throw new IllegalArgumentException("이미 삭제된 글입니다.");
        }

        String nickname2 = post.get().getNickName();

        if (!nickname.equals(nickname2)) {
            throw new IllegalArgumentException("작성자만 수정이 가능합니다.");
        }

        return postService.deletePost(postId);
    }

}
