package com.sparta.magazine_week2.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.magazine_week2.dto.request.CommonDto;
import com.sparta.magazine_week2.dto.request.CommonDto.ImgUrlDto;
import com.sparta.magazine_week2.dto.request.PostRequestDto;
import com.sparta.magazine_week2.dto.request.PostRequestDto.PostUpdate;
import com.sparta.magazine_week2.entity.*;
import com.sparta.magazine_week2.exception.ErrorCode;
import com.sparta.magazine_week2.exception.ErrorCustomException;
import com.sparta.magazine_week2.repository.LikeRepository;
import com.sparta.magazine_week2.repository.UserRepository;
import com.sparta.magazine_week2.repository.post.PostCommentRepository;
import com.sparta.magazine_week2.repository.post.PostImageRepository;
import com.sparta.magazine_week2.repository.post.PostRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PostServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    JPAQueryFactory queryFactory;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostImageRepository postImageRepository;

    @Autowired
    PostCommentRepository postCommentRepository;

    @Autowired
    LikeRepository likeRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    public void 게시글_수정() {
        // given
        User user = testAccountSet();
        Post post = testPostSet(user);
        List<ImgUrlDto> imgList = new ArrayList<>();
        PostUpdate update = new PostUpdate("title2", "content2", "RIGHT", imgList);

        // when
        Optional<Post> findPost = postRepository.findById(post.getId());
        Optional<User> writer = userRepository.findById(user.getId());

        String nickname = findPost.get().getNickname();
        String nickname2 = writer.get().getNickname();

        if (!nickname.equals(nickname2)) {
            throw new ErrorCustomException(ErrorCode.NO_MATCH_USER_ERROR);
        }

        findPost.get().update(update);
        Optional<Post> updatePost = postRepository.findById(findPost.get().getId());

        // then
        assertThat(updatePost.get().getTitle()).isEqualTo("title2");
        assertThat(updatePost.get().getContents()).isEqualTo("content2");
        assertThat(updatePost.get().getType()).isEqualTo(PostTypeEnum.RIGHT);

    }

    private User testAccountSet() {
        User testUser = User.builder()
                .username("username")
                .password("password")
                .nickname("nickname")
                .build();
        User userSaved = userRepository.save(testUser);
        em.flush();
        em.clear();
        return userSaved;
    }

    private Post testPostSet(User user) {
        Post post = Post.builder()
                .title("title")
                .contents("contents")
                .nickname(user.getNickname())
                .type("LEFT")
                .build();
        Post postSaved = postRepository.save(post);
        em.flush();
        em.clear();
        return postSaved;
    }

    private PostImage testPostImageSet(Post post, String test) {
        PostImage testPostImage = PostImage.builder()
                .post(post)
                .postImg(test)
                .build();
        PostImage postImagesaved = postImageRepository.save(testPostImage);

        em.flush();
        em.clear();
        return postImagesaved;
    }

    private PostComment testPostComment(Post post, User user) {
        PostComment comment = PostComment.builder()
                .user(user)
                .post(post)
                .comment("test1")
                .build();
        PostComment postCommentSaved = postCommentRepository.save(comment);
        em.flush();
        em.clear();
        return postCommentSaved;
    }

    private LikeNumber testLikeNumber(Post post, User user){
        LikeNumber likes = LikeNumber.builder()
                .post(post)
                .user(user)
                .build();
        LikeNumber save = likeRepository.save(likes);
        em.flush();
        em.clear();
        return save;
    }

}