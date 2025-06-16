package com.example.seniya_back.controller.userCourse;

import com.example.seniya_back.common.constants.ApiMappingPattern;
import com.example.seniya_back.common.enums.Category;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.course.response.CourseDetailResponseDto;
import com.example.seniya_back.dto.course.response.CourseListResponseDto;
import com.example.seniya_back.service.UserCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.USER_COURSE_API)
@RequiredArgsConstructor
public class userCourseController {
    private final UserCourseService userCourseService;

    // 수업 전체 조회
    @GetMapping
    public ResponseEntity<ResponseDto<List<CourseListResponseDto>>> getAllUserCourses() {
        ResponseDto<List<CourseListResponseDto>> response = userCourseService.getAllCourses();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 수업 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<CourseDetailResponseDto>> getUserCourseById(@PathVariable Long id) {
        ResponseDto<CourseDetailResponseDto> response = userCourseService.getCourseById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 카테고리 별 수업 검색
    @GetMapping("/{category}")
    public ResponseEntity<ResponseDto<List<CourseListResponseDto>>> getCoursesByCategory(
            @RequestParam("category") Category category
    ) {
        ResponseDto<List<CourseListResponseDto>> response = userCourseService.getCoursesByCategory(category);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
