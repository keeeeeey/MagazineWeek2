package com.sparta.magazine_week2.repository.post;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.magazine_week2.dto.response.PostResponseDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.sparta.magazine_week2.entity.QPostComment.postComment;

@RequiredArgsConstructor
public class PostCommentRepositoryImpl implements PostCommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PostResponseDto.PostComment> findPostCommentByPostId(Long postId) {
        return queryFactory
                .select(Projections.constructor(PostResponseDto.PostComment.class,
                        postComment.id,
                        postComment.user.id,
                        postComment.user.username,
                        postComment.user.profileImg,
                        postComment.comment,
                        postComment.modifiedAt
                ))
                .from(postComment)
                .where(postComment.post.id.eq(postId))
                .groupBy(postComment.id)
                .orderBy(postComment.modifiedAt.desc())
                .fetch();
    }

}
