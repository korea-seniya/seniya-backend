package com.example.seniya_back.dto.participation.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class ParticipationResponseDto { // 신청한 수업 목록 조회
    private Long participationId;
    private String category;
    private String title;
    private String description;
    private String trainerName;
    private LocalDate courseDate;
    private LocalTime courseStartTime;
    private LocalTime courseEndTime;
    private String courseRoom;

}
