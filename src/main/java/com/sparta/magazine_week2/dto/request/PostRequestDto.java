package com.sparta.magazine_week2.dto.request;

import com.sparta.magazine_week2.dto.request.CommonDto.ImgUrlDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class PostRequestDto {

    @Getter
    @NoArgsConstructor
    public static class PostCreate {
        private String title;
        private String contents;
        private String nickname;
        private String type;
    }

    @Getter
    @NoArgsConstructor
    public static class PostUpdate {
        private String title;
        private String contents;
        private String type;
        private List<ImgUrlDto> delete_img;
    }

    @Getter
    @NoArgsConstructor
    public static class PostComment {
        private String comment;
    }

}
