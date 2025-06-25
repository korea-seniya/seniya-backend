package com.example.seniya_back.dto.course.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseApplyResponseDto {
    private Long courseId;
    private Long userId;
    private String userName;
    private Long trainerId;
    private String trainerName;
    private String title;
    private LocalDateTime date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String room;
}
