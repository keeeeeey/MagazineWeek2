//package com.sparta.magazine_week2.service;
//
//import com.sparta.magazine_week2.dto.request.PostRequestDto;
//import com.sparta.magazine_week2.dto.response.PostResponseDto;
//import com.sparta.magazine_week2.dto.response.UserResponseDto;
//import com.sparta.magazine_week2.entity.Post;
//import com.sparta.magazine_week2.entity.PostTypeEnum;
//import com.sparta.magazine_week2.repository.post.PostRepository;
//import com.sparta.magazine_week2.security.UserDetailsImpl;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static com.sparta.magazine_week2.entity.PostTypeEnum.RIGHT;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@ExtendWith(MockitoExtension.class)
//class PostServiceTest {
//
//    @Mock
//    PostRepository postRepository;
//
//    @Test
//    @DisplayName("포스트 수정")
//    @Order(1)
//    void update() {
//        Long postId = 100L;
//
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
//        PostService postService = new PostService(postRepository);
//        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
//
//        PostRequestDto requestDto2 = new PostRequestDto("내용2", nickName, likeCount, image, type);
//
//        postService.update(requestDto2, postId);
//
//        Optional<Post> post2 = postRepository.findById(postId);
//
//        assertEquals("내용2", post2.get().getContents());
//
//
//    }
//
//    @Test
//    @DisplayName("포스트 삭제")
//    @Order(2)
//    void deletePost() {
//        Long postId = 100L;
//        PostService postService = new PostService(postRepository);
//
//        postService.deletePost(postId);
//
//        Optional<Post> post = postRepository.findById(postId);
//
//        assertEquals(Optional.empty(), post);
//    }
//
//}