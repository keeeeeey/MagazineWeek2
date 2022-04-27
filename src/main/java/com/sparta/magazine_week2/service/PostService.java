package com.sparta.magazine_week2.service;

import com.sparta.magazine_week2.dto.request.PostRequestDto.PostCreate;
import com.sparta.magazine_week2.dto.request.PostRequestDto.PostUpdate;
import com.sparta.magazine_week2.dto.response.PostResponseDto;
import com.sparta.magazine_week2.dto.response.PostResponseDto.DetailPost;
import com.sparta.magazine_week2.entity.LikeNumber;
import com.sparta.magazine_week2.entity.Post;
import com.sparta.magazine_week2.entity.PostImage;
import com.sparta.magazine_week2.exception.ErrorCode;
import com.sparta.magazine_week2.exception.ErrorCustomException;
import com.sparta.magazine_week2.repository.BatchInsertRepository;
import com.sparta.magazine_week2.repository.LikeRepository;
import com.sparta.magazine_week2.repository.UserRepository;
import com.sparta.magazine_week2.repository.post.PostCommentRepository;
import com.sparta.magazine_week2.repository.post.PostImageRepository;
import com.sparta.magazine_week2.repository.post.PostRepository;

import com.sparta.magazine_week2.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final PostCommentRepository postCommentRepository;
    private final AwsS3Service awsS3Service;
    private final PostImageRepository postImageRepository;
    private final BatchInsertRepository batchInsertRepository;

    @Transactional
    public Long createPost(PostCreate requestDto, UserDetailsImpl userDetails, List<MultipartFile> imgFile) {
        Post post = Post.builder()
                .title(requestDto.getTitle())
                .contents(requestDto.getContents())
                .nickname(userDetails.getUser().getNickname())
                .type(requestDto.getType())
                .build();

        setImgList(post, imgFile);

        postRepository.save(post);
        return post.getId();
    }

    @Transactional(readOnly = true)
    public List<Post> getPostList(int start) {
        Pageable pageable = PageRequest.of(start, 10);
        List<Post> posts = postRepository.findAllByOrderByModifiedAtDesc(pageable);
        return posts;
    }

    @Transactional(readOnly = true)
    public DetailPost getPost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR));
        List<PostResponseDto.PostComment> postCommentList = postCommentRepository.findPostCommentByPostId(postId);

        boolean isLike = likeRepository.findByPostIdAndUserId(postId, userId);
        List<PostImage> imgList = postImageRepository.findByPostId(postId);

        List<LikeNumber> likeNumberList = likeRepository.findAllByPostId(postId);

        return DetailPost.builder()
                .post(post)
                .is_like(isLike)
                .commentList(postCommentList)
                .postImage(imgList)
                .likeCount((long) likeNumberList.size())
                .build();
    }

    @Transactional
    public Long updatePost(PostUpdate requestDto, Long postId, UserDetailsImpl userDetails, List<MultipartFile> imgFile) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR));

        String nickname = userDetails.getUser().getNickname();
        String nickname2 = post.getNickname();

        if (!nickname.equals(nickname2)) {
            throw new IllegalArgumentException("작성자만 수정이 가능합니다.");
        }

        if (requestDto.getDelete_img().size() != 0) {
            requestDto.getDelete_img().forEach((img) -> {
                awsS3Service.deleteImage(img.getImg_url());
                postImageRepository.deleteByPostImg(img.getImg_url());
            });
        }

        setImgList(post, imgFile);
        post.update(requestDto);

        return post.getId();

    }

    @Transactional
    public void deletePost(Long postId, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ErrorCustomException(ErrorCode.NONEXISTENT_ERROR));
        String nickname = userDetails.getUser().getNickname();
        String nickname2 = post.getNickname();

        if (!nickname.equals(nickname2)) {
            throw new ErrorCustomException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }

        List<PostImage> imgList = postImageRepository.findByPostId(postId);
        imgList.forEach((img) -> {
            awsS3Service.deleteImage(img.getPostImg());
        });

        likeRepository.deleteAllByPostId(postId);
        postRepository.deleteById(postId);
    }

    private void setImgList(Post post, List<MultipartFile> imgFile) {
        if (imgFile.size() != 0) {
            List<PostImage> imgList = new ArrayList<>();
            List<String> fileUrlList = awsS3Service.uploadImage(imgFile);
            fileUrlList.forEach((fileUrl) -> {
                PostImage postImage = PostImage.builder()
                        .postImg(fileUrl)
                        .post(post)
                        .build();
                imgList.add(postImage);
            });
            batchInsertRepository.postImageSaveAll(imgList);
        }
    }

}