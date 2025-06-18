package com.example.seniya_back.dto.comment.response;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentUpdateResponseDto {
    private Long commentId;
    private String content;
    private LocalDateTime updatedAt;

    public CommentUpdateResponseDto(Long commentId, String content, LocalDateTime updatedAt) {
        this.commentId = commentId;
        this.content = content;
        this.updatedAt = updatedAt;
    }
}