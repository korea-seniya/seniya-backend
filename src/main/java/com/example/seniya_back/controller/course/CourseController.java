package com.example.seniya_back.controller.course;

import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.admin.course.request.CourseCreateReqDto;
import com.example.seniya_back.dto.admin.course.request.CourseUpdateReqDto;
import com.example.seniya_back.dto.admin.course.response.CourseDetailRespDto;
import com.example.seniya_back.dto.admin.course.response.CourseRespDto;
import com.example.seniya_back.dto.admin.course.response.CourseUpdateRespDto;
import com.example.seniya_back.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    // 수업 생성
    @PostMapping
    public ResponseEntity<ResponseDto<CourseRespDto>> createCourse(@RequestBody CourseCreateReqDto dto) {
        ResponseDto<CourseRespDto> response = courseService.createCourse(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 수업 수정
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<CourseUpdateRespDto>> updateCourse(@PathVariable Long id, @RequestBody CourseUpdateReqDto dto) {
        ResponseDto<CourseUpdateRespDto> response = courseService.updateCourse(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 수업 전체 조회
    @GetMapping
    public ResponseEntity<ResponseDto<List<CourseRespDto>>> getAllCourses() {
        ResponseDto<List<CourseRespDto>> response = courseService.getAllCourses();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 수업 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<CourseDetailRespDto>> getCourseById(@PathVariable Long id) {
        ResponseDto<CourseDetailRespDto> response = courseService.getCourseById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 수업 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> deleteCourse(@PathVariable Long id) {
        ResponseDto<?> response = courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }


}
