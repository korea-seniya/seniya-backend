package com.example.seniya_back.controller.userCourse;

import com.example.seniya_back.common.constants.ApiMappingPattern;
import com.example.seniya_back.common.enums.Category;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.course.response.CourseApplyResponseDto;
import com.example.seniya_back.dto.course.response.CourseDetailResponseDto;
import com.example.seniya_back.dto.course.response.CourseListResponseDto;
import com.example.seniya_back.service.UserCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping("/{id}")
    public ResponseEntity<ResponseDto<CourseApplyResponseDto>> applyCourse(
            @AuthenticationPrincipal String username,
            @PathVariable Long id
    ) {
        ResponseDto<CourseApplyResponseDto> response = userCourseService.applyCourse(username, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(params = "category")
    public ResponseEntity<ResponseDto<List<CourseListResponseDto>>> getCoursesByCategory(
            @RequestParam(required = false) Category category
    ) {
        ResponseDto<List<CourseListResponseDto>> responseDto = userCourseService.getCoursesByCategory(category);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping(params = "trainerName")
    public ResponseEntity<ResponseDto<List<CourseListResponseDto>>> getCoursesByTrainerName(
            @RequestParam String trainerName
    ) {
        ResponseDto<List<CourseListResponseDto>> responseDto = userCourseService.getCoursesByTrainerName(trainerName);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
