package com.sparta.magazine_week2.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class PostRequestDto {

    @Getter
    @NoArgsConstructor
    public static class PostCreate {
        private String title;
        private String contents;
        private String nickName;
        private int likeCount;
        private String image;
        private String type;
    }

    @Getter
    @NoArgsConstructor
    public static class PostUpdate {
        private String title;
        private String contents;
        private String nickName;
        private String image;
        private String type;
    }

}
