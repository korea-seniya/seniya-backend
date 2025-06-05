package com.example.seniya_back.dto.post.response;

import com.example.seniya_back.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class PostListResponseDto {
    private Long id;
    private User userName;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
