package com.example.seniya_back.dto.comment.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentUpdateRequestDto { // 댓글 수정

    @NotBlank(message = "수정할 댓글 내용을 입력해주세요.")
    private String content;
}
