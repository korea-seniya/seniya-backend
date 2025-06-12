package com.example.seniya_back.dto.notice.response;

import com.example.seniya_back.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class NoticeDetailResponseDto { // 단권조회
    private Long noticeId;
    private String title;
    private String content;
}

