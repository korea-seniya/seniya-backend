package com.example.seniya_back.dto.notice.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class NoticeUpdateResponseDto { // 공지사항 수정
    private String username;
    private Long noticeId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
