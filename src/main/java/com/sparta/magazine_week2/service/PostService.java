package com.sparta.magazine_week2.service;

import com.sparta.magazine_week2.dto.PostUpdateRequestDto;
import com.sparta.magazine_week2.dto.UserResponseDto;
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
    public UserResponseDto update(PostUpdateRequestDto updateRequestDto){
        UserResponseDto responseDto = new UserResponseDto();
        Post post = postRepository.findById(updateRequestDto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("아이디가 존재하지 않습니다."));

        post.update(updateRequestDto);

        responseDto.setResult(true);
        responseDto.setMsg("success");
        return responseDto;

    }

}