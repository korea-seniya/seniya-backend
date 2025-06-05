package com.example.seniya_back.service;

import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.admin.course.request.CourseCreateReqDto;
import com.example.seniya_back.dto.admin.course.request.CourseUpdateReqDto;
import com.example.seniya_back.dto.admin.course.response.CourseDetailRespDto;
import com.example.seniya_back.dto.admin.course.response.CourseRespDto;
import com.example.seniya_back.dto.admin.course.response.CourseUpdateRespDto;

import java.util.List;

public interface CourseService {
    ResponseDto<CourseRespDto> createCourse(CourseCreateReqDto dto);

    ResponseDto<CourseUpdateRespDto> updateCourse(Long id, CourseUpdateReqDto dto);

    ResponseDto<List<CourseRespDto>> getAllCourses();

    ResponseDto<CourseDetailRespDto> getCourseById(Long id);

    ResponseDto<?> deleteCourse(Long id);
}
