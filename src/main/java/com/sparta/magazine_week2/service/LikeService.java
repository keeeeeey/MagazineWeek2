package com.sparta.magazine_week2.service;

import com.sparta.magazine_week2.dto.LikeRequestDto;
import com.sparta.magazine_week2.dto.PostUpdateRequestDto;
import com.sparta.magazine_week2.dto.UserResponseDto;
import com.sparta.magazine_week2.entity.LikeNumber;
import com.sparta.magazine_week2.entity.Post;
import com.sparta.magazine_week2.repository.LikeRepository;
import com.sparta.magazine_week2.repository.PostRepository;
import com.sparta.magazine_week2.security.UserDetailsImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    public LikeService(LikeRepository likeRepository, PostRepository postRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    public UserResponseDto likeNotLike(LikeRequestDto likeRequestDto, UserDetailsImpl userDetails){
        UserResponseDto responseDto = new UserResponseDto();
        String username = userDetails.getUsername();

        if (username == null) {
            responseDto.setMsg("로그인 후 이용 가능합니다.");
            responseDto.setResult(false);
            return responseDto;
        }

        //이미 값이 있을 때
        List<LikeNumber> list = likeRepository.findByPostIdAndUserId(likeRequestDto.getPostId(),likeRequestDto.getUserId());
        if (list.size() != 0) {
            likeRepository.deleteById(list.get(0).getId());

            //해당 POST 게시물 likecount +1 해주기
            Post post = postRepository.findById(likeRequestDto.getPostId())
                    .orElseThrow(() -> new IllegalArgumentException("아이디가 존재하지 않습니다."));

            PostUpdateRequestDto updateRequestDto = new PostUpdateRequestDto(post);
            updateRequestDto.setLikeCount(updateRequestDto.getLikeCount() - 1);

            post.update(updateRequestDto);

            //리턴

            responseDto.setResult(true);
            responseDto.setMsg("좋아요 취소 성공");

            return responseDto;
        }
        //좋아요 DB 저장
        LikeNumber likeNumber = new LikeNumber(likeRequestDto);
        likeRepository.save(likeNumber);

        //해당 POST 게시물 likecount +1 해주기
        Post post = postRepository.findById(likeRequestDto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("아이디가 존재하지 않습니다."));

        PostUpdateRequestDto updateRequestDto = new PostUpdateRequestDto(post);
        updateRequestDto.setLikeCount(updateRequestDto.getLikeCount() + 1);

        post.update(updateRequestDto);

        //리턴

        responseDto.setResult(true);
        responseDto.setMsg("좋아요 성공");

        return responseDto;
    }

}
