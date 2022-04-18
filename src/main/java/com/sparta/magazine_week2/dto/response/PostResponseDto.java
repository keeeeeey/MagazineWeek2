package com.sparta.magazine_week2.dto.response;

import com.sparta.magazine_week2.entity.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class PostResponseDto {

    @Getter
    @NoArgsConstructor
    public static class PostList {
        private List<Post> total;

        @Builder
        public PostList(List<Post> total) {
            this.total = total;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class DetailPost {
        private Post post;
        private Boolean isLike;

        @Builder
        public DetailPost(final Post post, final Boolean isLike) {
            this.post = post;
            this.isLike = isLike;
        }
    }

}
