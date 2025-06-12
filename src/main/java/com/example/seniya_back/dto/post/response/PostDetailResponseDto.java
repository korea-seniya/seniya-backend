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
    private String authorName;
    private List<String> imageUrls;
    private List<CommentDto> comments;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class CommentDto {
        private Long commentId;
        private String authorName;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}