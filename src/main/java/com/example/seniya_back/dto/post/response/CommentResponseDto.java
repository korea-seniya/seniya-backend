package com.example.seniya_back.dto.post.response;

import com.example.seniya_back.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private String name;
    private String content;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.name = comment.getUser().getName();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }
}
