package com.example.seniya_back.dto.comment.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class CommentCreateResponseDto {
    private String username;
    private Long commentId;
    private Long postId;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;

    public CommentCreateResponseDto(Long commentId, Long postId, String username, String content, LocalDateTime createdAt) {
        this.commentId = commentId;
        this.postId = postId;
        this.username = username;
        this.content = content;
        this.createdAt = createdAt;
    }
}
