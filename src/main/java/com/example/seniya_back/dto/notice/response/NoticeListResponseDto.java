package com.example.seniya_back.dto.notice.response;


import com.example.seniya_back.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class NoticeListResponseDto {
    private String username;
    private Long noticeId;
    private String title;
    private LocalDateTime createdAt;
}
