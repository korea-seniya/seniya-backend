package com.example.seniya_back.service;

import com.example.seniya_back.common.enums.Category;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.course.response.CourseDetailResponseDto;
import com.example.seniya_back.dto.course.response.CourseListResponseDto;

import java.util.List;

public interface UserCourseService {
    ResponseDto<List<CourseListResponseDto>> getAllCourses();

    ResponseDto<CourseDetailResponseDto> getCourseById(Long id);

    ResponseDto<List<CourseListResponseDto>> getCoursesByCategory(Category category);

    ResponseDto<List<CourseListResponseDto>> getCoursesByTrainerName(String trainerName);
}
