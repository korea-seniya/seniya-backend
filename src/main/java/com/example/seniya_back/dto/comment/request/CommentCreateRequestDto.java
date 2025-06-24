package com.example.seniya_back.dto.comment.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCreateRequestDto { // 댓글 작성

    private String name;
    @NotBlank(message = "댓글 내용을 입력해주세요.")
    private String content;
}
