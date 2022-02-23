package com.sparta.magazine_week2.dto;

import com.sparta.magazine_week2.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostResponseDto {
    private String username;
    private List<Post> posts;
}
