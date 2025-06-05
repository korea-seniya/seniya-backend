package com.example.seniya_back.dto.admin.course.response;

import com.example.seniya_back.common.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class GetAllCourseResponseDto {
    private String name;
    private String title;
    private LocalDateTime classDate;
    private Category category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String classroom;
}
