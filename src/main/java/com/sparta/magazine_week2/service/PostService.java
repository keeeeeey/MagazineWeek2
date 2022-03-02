package com.sparta.magazine_week2.service;

import com.sparta.magazine_week2.dto.request.PostRequestDto;
import com.sparta.magazine_week2.dto.response.UserResponseDto;
import com.sparta.magazine_week2.entity.Post;
import com.sparta.magazine_week2.repository.PostRepository;

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

    public UserResponseDto deletePost(Long postId) {
        UserResponseDto responseDto = new UserResponseDto();

        postRepository.deleteById(postId);

        responseDto.setResult(true);
        responseDto.setMsg("success");
        return responseDto;
    }

    @Transactional // 메소드 동작이 SQL 쿼리문임을 선언합니다.
    public Long updateLikeCount(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("이미 삭제된 글입니다.")
        );
        post.pluslike();
        return id;
    }

    @Transactional // 메소드 동작이 SQL 쿼리문임을 선언합니다.
    public Long minusLikeCount(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NullPointerException("이미 삭제된 글입니다.")
        );
        post.minuslike();
        return id;
    }
}