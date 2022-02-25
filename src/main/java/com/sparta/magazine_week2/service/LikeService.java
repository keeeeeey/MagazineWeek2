package com.sparta.magazine_week2.service;

import com.sparta.magazine_week2.dto.LikeRequestDto;
import com.sparta.magazine_week2.dto.PostRequestDto;
import com.sparta.magazine_week2.dto.UserResponseDto;
import com.sparta.magazine_week2.entity.LikeNumber;
import com.sparta.magazine_week2.entity.Post;
import com.sparta.magazine_week2.repository.LikeRepository;
import com.sparta.magazine_week2.repository.PostRepository;
import com.sparta.magazine_week2.security.UserDetailsImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final PostService postService;

    public LikeService(LikeRepository likeRepository, PostRepository postRepository, PostService postService) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.postService = postService;
    }

    @Transactional
    public UserResponseDto likeNotLike(LikeRequestDto likeRequestDto, UserDetailsImpl userDetails){
        UserResponseDto responseDto = new UserResponseDto();

        if (userDetails == null) {
            throw new IllegalArgumentException("로그인 후 이용 가능합니다.");
        }

        //이미 값이 있을 때
        Optional<LikeNumber> likeNumber = likeRepository.findByPostIdAndUserId(likeRequestDto.getPostId(),likeRequestDto.getUserId());
        if (likeNumber.isPresent()) {
            likeRepository.deleteById(likeNumber.get().getId());

            postService.minusLikeCount(likeRequestDto.getPostId());

            responseDto.setResult(true);
            responseDto.setMsg("좋아요 취소 성공");

            return responseDto;
        } else {
            //좋아요 DB 저장
            LikeNumber likeNumbers = new LikeNumber(likeRequestDto);
            likeRepository.save(likeNumbers);

            postService.updateLikeCount(likeRequestDto.getPostId());

            responseDto.setResult(true);
            responseDto.setMsg("좋아요 성공");

            return responseDto;
        }
    }

}
