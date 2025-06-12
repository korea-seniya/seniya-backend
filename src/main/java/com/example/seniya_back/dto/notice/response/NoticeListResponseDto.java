package com.example.seniya_back.dto.notice.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class NoticeListResponseDto { // 공지사항 전체 조회
    private List<NoticeInfo> notices;
    private String message;

    @Getter
    @AllArgsConstructor
    public static class NoticeInfo {
        private String username;
        private String title;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }
}
