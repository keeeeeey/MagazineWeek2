package com.sparta.magazine_week2.service;

import com.sparta.magazine_week2.dto.request.PostRequestDto.PostCreate;
import com.sparta.magazine_week2.dto.request.PostRequestDto.PostUpdate;
import com.sparta.magazine_week2.dto.response.PostResponseDto;
import com.sparta.magazine_week2.dto.response.PostResponseDto.DetailPost;
import com.sparta.magazine_week2.entity.Post;
import com.sparta.magazine_week2.repository.LikeRepository;
import com.sparta.magazine_week2.repository.post.PostCommentRepository;
import com.sparta.magazine_week2.repository.post.PostRepository;

import com.sparta.magazine_week2.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final PostCommentRepository postCommentRepository;

    @Transactional
    public Long createPost(@RequestBody PostCreate requestDto,
                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Post post = Post.builder()
                .title(requestDto.getTitle())
                .contents(requestDto.getContents())
                .nickname(requestDto.getNickname())
                .image(requestDto.getImage())
                .type(requestDto.getType())
                .build();

        postRepository.save(post);
        return post.getId();
    }

    @Transactional(readOnly = true)
    public List<Post> getPostList() {
        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc();
        return posts;
    }

    @Transactional(readOnly = true)
    public DetailPost getPost(Long postId, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NullPointerException("존재하지 않는 게시글 입니다."));
        List<PostResponseDto.PostComment> postCommentList = postCommentRepository.findPostCommentByPostId(postId);
        if (userDetails != null) {
            boolean isLike = likeRepository.findByPostIdAndUserId(postId, userDetails.getUser().getId());

            return DetailPost.builder()
                    .post(post)
                    .is_like(isLike)
                    .commentList(postCommentList)
                    .build();
        }
        return DetailPost.builder()
                .post(post)
                .is_like(false)
                .commentList(postCommentList)
                .build();
    }

    @Transactional
    public Long update(PostUpdate requestDto, Long postId, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("아이디가 존재하지 않습니다."));

        String nickname = userDetails.getUser().getNickname();
        String nickname2 = post.getNickname();

        if (!nickname.equals(nickname2)) {
            throw new IllegalArgumentException("작성자만 수정이 가능합니다.");
        }

        post.update(requestDto);

        return post.getId();

    }

    @Transactional
    public void deletePost(Long postId, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("이미 삭제된 글입니다."));
        String nickname = userDetails.getUser().getNickname();
        String nickname2 = post.getNickname();

        if (!nickname.equals(nickname2)) {
            throw new IllegalArgumentException("작성자만 수정이 가능합니다.");
        }

        likeRepository.deleteAllByPostId(postId);
        postRepository.deleteById(postId);
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