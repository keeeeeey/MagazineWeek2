package com.sparta.magazine_week2.service;

import com.sparta.magazine_week2.dto.LikeRequestDto;
import com.sparta.magazine_week2.dto.PostUpdateRequestDto;
import com.sparta.magazine_week2.dto.UserResponseDto;
import com.sparta.magazine_week2.entity.LikeNumber;
import com.sparta.magazine_week2.entity.Post;
import com.sparta.magazine_week2.repository.LikeRepository;
import com.sparta.magazine_week2.repository.PostRepository;
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
    public UserResponseDto pluslike(LikeRequestDto likeRequestDto){
        UserResponseDto responseDto = new UserResponseDto();

        //이미 값이 있을 때
        int likecnt = likeRepository.findByPostIdAndUserId(likeRequestDto.getPostId(),likeRequestDto.getUserId()).size();
        if (likecnt != 0){
            responseDto.setResult(false);
            responseDto.setMsg("이미 존재합니다.");
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
        responseDto.setMsg("성공");
        return responseDto;
    }

    @Transactional
    public UserResponseDto dellike(LikeRequestDto likeRequestDto) {
        UserResponseDto responseDto = new UserResponseDto();

        //값이 존재하지 않을 때 (생길 일은 없음)
        int likecnt = likeRepository.findByPostIdAndUserId(likeRequestDto.getPostId(),likeRequestDto.getUserId()).size();
        if (likecnt == 0){
            responseDto.setResult(false);
            responseDto.setMsg("값이 존재하지 않습니다..");
            return responseDto;
        }

        //like 디비에서 삭제
        List<LikeNumber> likeinfo = likeRepository.findByPostIdAndUserId(likeRequestDto.getPostId(),likeRequestDto.getUserId());
        System.out.println(likeinfo.get(0).getId());
        likeRepository.deleteById(likeinfo.get(0).getId());

        //해당 POST 게시물 likecount  - 1 해주기
        Post post = postRepository.findById(likeRequestDto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("아이디가 존재하지 않습니다."));

        PostUpdateRequestDto updateRequestDto = new PostUpdateRequestDto(post);
        updateRequestDto.setLikeCount(updateRequestDto.getLikeCount() - 1);

        post.update(updateRequestDto);


        responseDto.setResult(true);
        responseDto.setMsg("성공");
        return responseDto;
    }
}
