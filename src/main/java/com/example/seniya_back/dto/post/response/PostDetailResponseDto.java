package com.example.seniya_back.dto.post.response;

import com.example.seniya_back.entity.User;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class PostDetailResponseDto {
    private Long id;
    private User username;
    private String title;
    private String content;
}