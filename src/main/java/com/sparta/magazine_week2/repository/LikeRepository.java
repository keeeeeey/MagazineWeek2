package com.sparta.magazine_week2.repository;

import com.sparta.magazine_week2.entity.LikeNumber;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeNumber, Long> {
    Optional<LikeNumber> findByPostIdAndUserId(Long postId, Long userId);
    List<LikeNumber> findAllByUserId(Long userId);
    void deleteAllByPostId(Long postId);
}
