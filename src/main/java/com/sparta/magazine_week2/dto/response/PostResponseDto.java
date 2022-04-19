package com.sparta.magazine_week2.dto.response;

import com.sparta.magazine_week2.entity.Post;
import com.sparta.magazine_week2.entity.Timestamped;
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
        private Boolean is_like;
        private List<PostComment> commentList;

        @Builder
        public DetailPost(final Post post, final Boolean is_like, final List<PostComment> commentList) {
            this.post = post;
            this.is_like = is_like;
            this.commentList = commentList;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class PostComment {
        private Long comment_id;
        private Long user_id;
        private String nickname;
        private String profile_img;
        private String comment;
        private Timestamped modify_time;

        @Builder
        public PostComment (final Long comment_id, final Long user_id, final String nickname, final String profile_img,
                            final String comment, final Timestamped modify_time) {
            this.comment_id = comment_id;
            this.user_id = user_id;
            this.nickname = nickname;
            this.profile_img = profile_img;
            this.comment = comment;
            this.modify_time = modify_time;
        }
    }

}
