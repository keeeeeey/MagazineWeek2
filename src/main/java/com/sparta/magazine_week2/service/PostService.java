package com.sparta.magazine_week2.service;

import com.sparta.magazine_week2.dto.PostRequestDto;
import com.sparta.magazine_week2.dto.UserResponseDto;
import com.sparta.magazine_week2.entity.Post;
import com.sparta.magazine_week2.repository.PostRepository;

import com.sparta.magazine_week2.security.UserDetailsImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public UserResponseDto update(PostRequestDto requestDto, Long postId){
        UserResponseDto responseDto = new UserResponseDto();
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("아이디가 존재하지 않습니다."));

        post.update(requestDto);

        responseDto.setResult(true);
        responseDto.setMsg("success");
        return responseDto;

    }

    public UserResponseDto deletePost(Long postId, UserDetailsImpl userDetails) {
        UserResponseDto responseDto = new UserResponseDto();

        Post post = postRepository.findById(postId).
                orElseThrow(() -> new IllegalArgumentException("이미 삭제된 글입니다."));

        String username = userDetails.getUsername();
        String username2 = post.getUsername();

        if (!username.equals(username2)) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }

        postRepository.deleteById(postId);

        responseDto.setResult(true);
        responseDto.setMsg("success");
        return responseDto;
    }
}