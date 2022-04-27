package com.sparta.magazine_week2.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommonDto {

    @Getter
    @NoArgsConstructor
    public static class ImgUrlDto {
        private String img_url;

        public ImgUrlDto(final String img_url) {
            this.img_url = img_url;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class Id {
        private Long id;
    }

}
