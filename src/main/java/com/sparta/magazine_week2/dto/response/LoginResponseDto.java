package com.sparta.magazine_week2.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class LoginResponseDto {

    @Getter
    @NoArgsConstructor
    public static class CommonLoginResponseDto {
        private Long user_id;
        private String nickname;
        private String access_token;

        @Builder
        public CommonLoginResponseDto(final Long user_id, final String nickname, final String access_token) {
            this.user_id = user_id;
            this.nickname = nickname;
            this.access_token = access_token;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class KakaoLoginResponseDto {
        private Long user_id;
        private String nickname;
        private String profile_img;
        private String access_token;

        @Builder
        public KakaoLoginResponseDto(final Long user_id, final String nickname, final String profile_img, final String access_token) {
            this.user_id = user_id;
            this.nickname = nickname;
            this.profile_img = profile_img;
            this.access_token = access_token;
        }
    }

}
