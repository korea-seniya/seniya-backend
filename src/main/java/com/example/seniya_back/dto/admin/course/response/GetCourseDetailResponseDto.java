package com.example.seniya_back.dto.admin.course.response;

import com.example.seniya_back.common.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor
public class GetCourseDetailResponseDto {
    private Long courseId;
    private Long trainerId;
    private String trainerName;
    private String title;
    private String description;
    private LocalDateTime classDate;
    private LocalTime classStartTime;
    private LocalTime classEndTime;
    private Category category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String classroom;
}
