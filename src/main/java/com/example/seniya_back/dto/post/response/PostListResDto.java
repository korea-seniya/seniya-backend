package com.example.seniya_back.dto.post.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class PostListResDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
}
