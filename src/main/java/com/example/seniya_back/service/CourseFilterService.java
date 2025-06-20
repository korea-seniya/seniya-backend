package com.example.seniya_back.service;

import com.example.seniya_back.common.enums.Category;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.admin.course.response.CourseResponseDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface CourseFilterService {
    ResponseDto<List<CourseResponseDto>> getTodayCourses();

    ResponseDto<List<CourseResponseDto>> quickSearchCourses(Category category, String trainer, LocalDate classDate, LocalTime classStartTime, LocalTime classEndTime);
}
