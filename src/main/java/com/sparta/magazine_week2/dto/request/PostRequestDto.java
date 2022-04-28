package com.sparta.magazine_week2.dto.request;

import com.sparta.magazine_week2.dto.request.CommonDto.ImgUrlDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class PostRequestDto {

    @Getter
    @NoArgsConstructor
    public static class PostCreate {

        @NotEmpty(message = "제목을 입력해주세요.")
        private String title;

        @NotEmpty(message = "내용을 입력해주세요.")
        private String contents;

        @NotEmpty(message = "타입을 지정해주세요.")
        private String type;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostUpdate {

        @NotEmpty(message = "제목을 입력해주세요.")
        private String title;

        @NotEmpty(message = "내용을 입력해주세요.")
        private String contents;

        @NotEmpty(message = "타입을 지정해주세요.")
        private String type;

        private List<ImgUrlDto> delete_img;
    }

    @Getter
    @NoArgsConstructor
    public static class PostComment {

        @NotEmpty(message = "댓글을 입력해주세요.")
        private String comment;

    }

}
