package com.example.seniya_back.controller.course;

import com.example.seniya_back.common.constants.ApiMappingPattern;
import com.example.seniya_back.common.enums.Category;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.admin.course.response.CourseResponseDto;
import com.example.seniya_back.service.CourseFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.COURSE_FILTER_API)
@RequiredArgsConstructor
public class CourseFilterController {

    private final CourseFilterService courseFilterService;

    // 오늘의 수업
    @GetMapping("/today")
    public ResponseEntity<ResponseDto<List<CourseResponseDto>>> getTodayCourses() {
        ResponseDto<List<CourseResponseDto>> response = courseFilterService.getTodayCourses();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    // 빠른 검색
    @GetMapping
    public ResponseEntity<ResponseDto<List<CourseResponseDto>>> quickSearchCourses(
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) String trainer,
            @RequestParam(required = false) LocalDate classDate
    ) {
        ResponseDto<List<CourseResponseDto>> response = courseFilterService.quickSearchCourses(category, trainer, classDate);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
