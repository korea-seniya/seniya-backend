package com.example.seniya_back.dto.participation.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
public class ParticipationResponseDto { // 신청한 수업 목록 조회

    private String name;
    private String title;
    private String description;
    private LocalDate classDate;
    private LocalTime classStartTime;
    private LocalTime classEndTime;
    private String category;
    private String classroom;

}
