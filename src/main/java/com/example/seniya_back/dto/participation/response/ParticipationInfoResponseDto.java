package com.example.seniya_back.dto.participation.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class ParticipationInfoResponseDto { // 취소된 수업 정보
    private String title;
    private String description;
    private String trainerName;
    private LocalDate courseDate;
    private LocalTime courseStartTime;
    private LocalTime courseEndTime;
    private String courseRoom;
}
