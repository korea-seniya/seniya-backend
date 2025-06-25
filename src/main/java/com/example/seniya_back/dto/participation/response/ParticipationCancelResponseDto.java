package com.example.seniya_back.dto.participation.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ParticipationCancelResponseDto { // 신청한 수업 취소

    private String message;  // 수업 취소 메시지
    private ParticipationInfoResponseDto data;

}
