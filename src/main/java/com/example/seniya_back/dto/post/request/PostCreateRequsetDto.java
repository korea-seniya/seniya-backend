package com.example.seniya_back.dto.post.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateRequsetDto {
    @NotBlank(message = "제목 입력은 필수 입니다.")
    private String title;

    @NotBlank(message = "내용 입력은 필수 입니다.")
    private String content;
}
