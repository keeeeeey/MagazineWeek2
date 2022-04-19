package com.sparta.magazine_week2.repository.post;

import com.sparta.magazine_week2.dto.response.PostResponseDto;

import java.util.List;

public interface PostCommentRepositoryCustom {

    List<PostResponseDto.PostComment> findPostCommentByPostId(Long postId);

}
