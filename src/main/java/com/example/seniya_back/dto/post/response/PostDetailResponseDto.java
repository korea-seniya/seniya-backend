package com.example.seniya_back.dto.post.response;

import com.example.seniya_back.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class PostDetailResponseDto {
    private Long postId;
    private User username;
    private String title;
    private String content;
}