package com.sparta.magazine_week2.service;

import com.sparta.magazine_week2.dto.request.PostRequestDto;
import com.sparta.magazine_week2.entity.Post;
import com.sparta.magazine_week2.entity.PostComment;
import com.sparta.magazine_week2.entity.User;
import com.sparta.magazine_week2.exception.ErrorCode;
import com.sparta.magazine_week2.exception.ErrorCustomException;
import com.sparta.magazine_week2.repository.UserRepository;
import com.sparta.magazine_week2.repository.post.PostCommentRepository;
import com.sparta.magazine_week2.repository.post.PostRepository;
import com.sparta.magazine_week2.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostCommentService {

    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createComment(PostRequestDto.PostComment requestDto, Long userId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() ->  new NullPointerException("존재하지 않는 계시글 입니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() ->  new ErrorCustomException(ErrorCode.NO_USER_ERROR));
        PostComment postComment = PostComment.builder()
                .comment(requestDto.getComment())
                .post(post)
                .user(user)
                .build();
        PostComment save = postCommentRepository.save(postComment);
        return save.getId();
    }

    @Transactional
    public Long updateComment(PostRequestDto.PostComment requestDto, Long userId, Long commentId) {
        PostComment postComment = commentValidation(userId, commentId);
        postComment.updateComment(requestDto.getComment());
        return postComment.getId();
    }

    @Transactional
    public void deleteComment(Long userId, Long commentId) {
        PostComment postComment = commentValidation(userId, commentId);
        postCommentRepository.deleteById(postComment.getId());
    }

    // 코멘트 수정삭제 권한 확인
    public PostComment commentValidation(Long userId, Long commentId) {
        PostComment postComment = postCommentRepository.findById(commentId).orElseThrow(
                () -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR));

        if (!postComment.getUser().getId().equals(userId)) {
            throw new ErrorCustomException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }

        return postComment;
    }

}
