package com.sparta.magazine_week2.dto.response;

import com.sparta.magazine_week2.entity.LikeNumber;
import com.sparta.magazine_week2.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private List<LikeNumber> myLike;
    private List<Post> total;

    @Builder
    public PostResponseDto(List<Post> total) {
        this.total = total;
    }
}
