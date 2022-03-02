//package com.sparta.magazine_week2.integration;
//
//import com.sparta.magazine_week2.controller.PostController;
//import com.sparta.magazine_week2.dto.request.PostRequestDto;
//import com.sparta.magazine_week2.entity.Post;
//import com.sparta.magazine_week2.entity.PostTypeEnum;
//import com.sparta.magazine_week2.service.PostService;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static com.sparta.magazine_week2.entity.PostTypeEnum.RIGHT;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class PostIntegrationTest {
//
//    @Autowired
//    PostService postService;
//
//    @Autowired
//    PostController postController;
//
//    Long postId = 100L;
//    Post createdPost = null;
//
//    @Test
//    @Order(1)
//    @DisplayName("포스트 등록")
//    void test1() {
//        String contents = "내용";
//        String nickName = "닉네임";
//        int likeCount = 10;
//        String image = "이미지 경로";
//        PostTypeEnum type = RIGHT;
//
//        PostRequestDto requestDto = new PostRequestDto(contents, nickName, likeCount, image, type);
//
//        Post post = new Post(requestDto);
//
//
//    }
//}
