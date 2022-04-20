package com.sparta.magazine_week2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.magazine_week2.dto.KakaoUserInfoDto;
import com.sparta.magazine_week2.dto.response.LoginResponseDto;
import com.sparta.magazine_week2.dto.response.LoginResponseDto.KakaoLoginResponseDto;
import com.sparta.magazine_week2.entity.User;
import com.sparta.magazine_week2.repository.UserRepository;
import com.sparta.magazine_week2.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KakaoService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public KakaoLoginResponseDto kakaoLogin(String code) throws JsonProcessingException{
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getAccessToken(code);

        // 2. 토큰으로 카카오 API 호출
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);

        // 3. 필요시에 회원가입, JWT 토큰 발행
        return registerKakaoUserIfNeeded(kakaoUserInfo);
    }

    private String getAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "a3812aeb3bcc5d986824bbbc83d5a98f");
        body.add("redirect_uri", "http://localhost:8081/user/kakao/callback");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String profileImage = jsonNode.get("properties")
                .get("profile_image").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();
        String password = UUID.randomUUID().toString();

        System.out.println("카카오 사용자 정보: " + id + ", " + nickname + ", " + profileImage + ", " + email);
        return new KakaoUserInfoDto(id, nickname, profileImage, email, password);
    }

    private KakaoLoginResponseDto registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {
        // DB 에 중복된 Kakao Id 가 있는지 확인
        String username = kakaoUserInfo.getEmail();

        User kakaoUser = userRepository.findByUsername(username)
                .orElse(null);

        if (kakaoUser == null) {
            // 회원가입
            String nickname = kakaoUserInfo.getNickname();
            String profileImg = kakaoUserInfo.getProfile_img();
            String password = kakaoUserInfo.getPassword();

            kakaoUser = User.builder()
                    .username(username)
                    .nickname(nickname)
                    .profileImg(profileImg)
                    .password(password)
                    .build();
            userRepository.save(kakaoUser);
        }

        String accessToken = jwtTokenProvider.createToken(kakaoUser.getUsername());
//        String refreshToken = jwtTokenProvider.createRefreshToken(Long.toString(kakaoUser.getId()));
//        redisService.setValues(refreshToken, kakaoUser.getId());
        return KakaoLoginResponseDto.builder()
                .user_id(kakaoUser.getId())
                .nickname(kakaoUser.getNickname())
                .profile_img(kakaoUser.getProfileImg())
                .access_token(accessToken)
                .build();
    }
}
