package com.example.seniya_back.dto.admin.course.request;

import com.example.seniya_back.common.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseUpdateReqDto {
    private Long trainerId;
    private String title;
    private String description;
    private LocalDateTime classDate;
    private LocalTime classStartTime;
    private LocalTime classEndTime;
    private Category category;
    private String classroom;
}
