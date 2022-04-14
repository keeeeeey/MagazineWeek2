package com.sparta.magazine_week2.service;

import com.sparta.magazine_week2.dto.request.LikeRequestDto;
import com.sparta.magazine_week2.entity.LikeNumber;
import com.sparta.magazine_week2.entity.Post;
import com.sparta.magazine_week2.entity.User;
import com.sparta.magazine_week2.repository.LikeRepository;
import com.sparta.magazine_week2.repository.PostRepository;
import com.sparta.magazine_week2.repository.UserRepository;
import com.sparta.magazine_week2.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostService postService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void likeNotLike(LikeRequestDto likeRequestDto, UserDetailsImpl userDetails){
        //이미 값이 있을 때
        Optional<LikeNumber> likeNumber = likeRepository.findByPostIdAndUserId(likeRequestDto.getPostId(),likeRequestDto.getUserId());
        if (likeNumber.isPresent()) {
            likeRepository.deleteById(likeNumber.get().getId());

            postService.minusLikeCount(likeRequestDto.getPostId());

        } else {
            //좋아요 DB 저장
            Post post = postRepository.findById(likeRequestDto.getPostId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

            User user = userRepository.findById(likeRequestDto.getUserId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

            LikeNumber likeNumbers = LikeNumber.builder()
                                        .user(user)
                                        .post(post)
                                        .build();

            likeRepository.save(likeNumbers);

            postService.updateLikeCount(likeRequestDto.getPostId());
        }
    }

}
