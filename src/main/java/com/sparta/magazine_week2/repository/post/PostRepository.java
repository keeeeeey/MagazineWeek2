package com.sparta.magazine_week2.repository.post;

import com.sparta.magazine_week2.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByModifiedAtDesc(Pageable pageable);
}
