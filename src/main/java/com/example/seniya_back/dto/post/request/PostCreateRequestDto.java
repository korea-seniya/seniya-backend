package com.example.seniya_back.dto.post.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostCreateRequestDto {
    @NotBlank(message = "제목 입력은 필수 입니다.")
    private String title;

    @NotBlank(message = "내용 입력은 필수 입니다.")
    private String content;
}