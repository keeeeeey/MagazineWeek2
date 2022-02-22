package com.sparta.magazine_week2.repository;

import com.sparta.magazine_week2.entity.LikeNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface LikeRepository extends JpaRepository<LikeNumber, Long> {
    List<LikeNumber> findByPostIdAndUserId(Long postId, Long userId);
}
