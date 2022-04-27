package com.sparta.magazine_week2.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.magazine_week2.entity.*;
import com.sparta.magazine_week2.repository.post.PostCommentRepository;
import com.sparta.magazine_week2.repository.post.PostImageRepository;
import com.sparta.magazine_week2.repository.post.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PostRepositoryTests {

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
    public void 전체글_조회() throws Exception {
        // given
        User user = testAccountSet();

        Post post1 = testPostSet(user);
        Post post2 = testPostSet(user);
        Post post3 = testPostSet(user);

        LikeNumber likeNumber = testLikeNumber(post1, user);
        LikeNumber likeNumber2 = testLikeNumber(post2, user);
        LikeNumber likeNumber3 = testLikeNumber(post2, user);

        Pageable pageable = PageRequest.of(0, 10);

        // when
        List<Post> postList = postRepository.findAllByOrderByModifiedAtDesc(pageable);
        List<LikeNumber> likeList = likeRepository.findAllByPostId(post1.getId());
        List<LikeNumber> likeList2 = likeRepository.findAllByPostId(post2.getId());

        //then
        assertThat(postList.size()).isEqualTo(3);
        assertThat(user.getUsername()).isEqualTo("username");
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getNickname()).isEqualTo("nickname");
        assertThat(postList.get(0).getTitle()).isEqualTo("title");
        assertThat(postList.get(0).getContents()).isEqualTo("contents");
        assertThat(postList.get(0).getNickname()).isEqualTo("nickname");
        assertThat(postList.get(0).getType()).isEqualTo(PostTypeEnum.LEFT);
        assertThat(likeList.size()).isEqualTo(1);
        assertThat(likeList2.size()).isEqualTo(2);

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
        PostComment postCommentsaved = postCommentRepository.save(comment);
        em.flush();
        em.clear();
        return postCommentsaved;
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
