package com.sparta.magazine_week2.service;

import com.sparta.magazine_week2.dto.request.PostRequestDto;
import com.sparta.magazine_week2.entity.Post;
import com.sparta.magazine_week2.entity.PostComment;
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

    @Transactional
    public Long createComment(PostRequestDto.PostComment requestDto, UserDetailsImpl userDetails, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() ->  new NullPointerException("존재하지 않는 계시글 입니다."));
        PostComment postComment = PostComment.builder()
                .comment(requestDto.getComment())
                .post(post)
                .user(userDetails.getUser())
                .build();
        PostComment save = postCommentRepository.save(postComment);
        return save.getId();
    }

    @Transactional
    public Long updateComment(PostRequestDto.PostComment requestDto, UserDetailsImpl userDetails, Long commentId) {
        PostComment postComment = commentValidation(userDetails.getUser().getId(), commentId);
        postComment.updateComment(requestDto.getComment());
        return postComment.getId();
    }

    @Transactional
    public void deleteComment(UserDetailsImpl userDetails, Long commentId) {
        PostComment postComment = commentValidation(userDetails.getUser().getId(), commentId);
        postCommentRepository.deleteById(postComment.getId());
    }

    // 코멘트 수정삭제 권한 확인
    public PostComment commentValidation(Long userId, Long commentId) {
        PostComment postComment = postCommentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 댓글입니다.")
        );
        if (!postComment.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("작성자만 수정이 가능합니다.");
        }
        return postComment;
    }

}
