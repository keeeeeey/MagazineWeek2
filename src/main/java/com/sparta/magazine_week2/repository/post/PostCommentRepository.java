package com.sparta.magazine_week2.repository.post;

import com.sparta.magazine_week2.entity.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment, Long>, PostCommentRepositoryCustom {
}
