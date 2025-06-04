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
public class CourseRespDto {
    private String name;
    private String title;
    private String description;
    private LocalDateTime classDate;
    private LocalTime classStartTime;
    private LocalDateTime classEndTime;
    private Category category;
    private String classroom;
}
