package com.example.seniya_back.dto.post.response;

import com.example.seniya_back.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PostDetailResponseDto {
    private Long postId;
    private String title;
    private String content;
    private String username;
    private List<String> imageUrls;
    private List<CommentDto> comments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime modifiedAt;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class CommentDto {
        private Long commentId;
        private String name;
        private String content;
        private LocalDateTime createdAt;
    }
}