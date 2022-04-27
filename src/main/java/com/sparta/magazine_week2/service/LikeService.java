package com.sparta.magazine_week2.service;

import com.sparta.magazine_week2.entity.LikeNumber;
import com.sparta.magazine_week2.entity.Post;
import com.sparta.magazine_week2.entity.User;
import com.sparta.magazine_week2.exception.ErrorCode;
import com.sparta.magazine_week2.exception.ErrorCustomException;
import com.sparta.magazine_week2.repository.LikeRepository;
import com.sparta.magazine_week2.repository.post.PostRepository;
import com.sparta.magazine_week2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostService postService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void likeNotLike(Long postId, Long userId){
        //이미 값이 있을 때
        boolean isLike = likeRepository.findByPostIdAndUserId(postId, userId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ErrorCustomException(ErrorCode.NO_USER_ERROR));

        if (isLike) {
            likeRepository.deleteByPostIdAndUserId(post.getId(), user.getId());
            post.minuslike();
        } else {
            //좋아요 DB 저장
            LikeNumber likeNumbers = LikeNumber.builder()
                                        .user(user)
                                        .post(post)
                                        .build();

            likeRepository.save(likeNumbers);
            post.pluslike();
        }
    }

}
