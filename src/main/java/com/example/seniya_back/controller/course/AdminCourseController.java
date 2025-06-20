package com.example.seniya_back.controller.course;

import com.example.seniya_back.common.constants.ApiMappingPattern;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.admin.course.request.CreateCourseRequestDto;
import com.example.seniya_back.dto.admin.course.request.UpdateCourseRequestDto;
import com.example.seniya_back.dto.admin.course.response.GetCourseDetailResponseDto;
import com.example.seniya_back.dto.admin.course.response.CourseResponseDto;
import com.example.seniya_back.dto.admin.course.response.UpdateCourseResponseDto;
import com.example.seniya_back.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.ADMIN_COURSE_API)
@RequiredArgsConstructor
public class AdminCourseController {

    private final CourseService courseService;

    // 수업 생성
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ResponseDto<CourseResponseDto>> createCourse(@RequestBody CreateCourseRequestDto dto) {
        ResponseDto<CourseResponseDto> response = courseService.createCourse(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 수업 수정
//    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<UpdateCourseResponseDto>> updateCourse(@PathVariable Long id, @RequestBody UpdateCourseRequestDto dto) {
        ResponseDto<UpdateCourseResponseDto> response = courseService.updateCourse(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 수업 전체 조회
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ResponseDto<List<CourseResponseDto>>> getAllCourses() {
        ResponseDto<List<CourseResponseDto>> response = courseService.getAllCourses();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 수업 단건 조회
//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<GetCourseDetailResponseDto>> getCourseById(@PathVariable Long id) {
        ResponseDto<GetCourseDetailResponseDto> response = courseService.getCourseById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 수업 삭제
//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto<?>> deleteCourse(@PathVariable Long id) {
        ResponseDto<?> response = courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }


}
