package com.example.seniya_back.service;

import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.admin.course.request.CreateCourseRequestDto;
import com.example.seniya_back.dto.admin.course.request.UpdateCourseRequestDto;
import com.example.seniya_back.dto.admin.course.response.GetCourseDetailResponseDto;
import com.example.seniya_back.dto.admin.course.response.CourseResponseDto;
import com.example.seniya_back.dto.admin.course.response.UpdateCourseResponseDto;

import java.util.List;

public interface CourseService {
    ResponseDto<CourseResponseDto> createCourse(CreateCourseRequestDto dto);

    ResponseDto<UpdateCourseResponseDto> updateCourse(Long id, UpdateCourseRequestDto dto);

    ResponseDto<List<CourseResponseDto>> getAllCourses();

    ResponseDto<GetCourseDetailResponseDto> getCourseById(Long id);

    ResponseDto<?> deleteCourse(Long id);
}
