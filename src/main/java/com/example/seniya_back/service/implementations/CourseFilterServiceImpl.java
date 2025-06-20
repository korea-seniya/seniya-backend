package com.example.seniya_back.service.implementations;

import com.example.seniya_back.common.constants.ResponseCode;
import com.example.seniya_back.common.constants.ResponseMessage;
import com.example.seniya_back.common.enums.Category;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.admin.course.response.CourseResponseDto;
import com.example.seniya_back.entity.Course;
import com.example.seniya_back.repository.CourseRepository;
import com.example.seniya_back.service.CourseFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseFilterServiceImpl implements CourseFilterService {

    private final CourseRepository courseRepository;

    @Override
    public ResponseDto<List<CourseResponseDto>> getTodayCourses() {
        List<CourseResponseDto> respDtos = null;

        LocalDate today = LocalDate.now();


        List<Course> findCourses = courseRepository.findAll().stream().
                filter(course -> course.getDate().toLocalDate().equals(today))
                .collect(Collectors.toList());

        respDtos = findCourses.stream()
                .map(course -> CourseResponseDto.builder()
                        .id(course.getCourseId())
                        .trainerId(course.getTrainerProfile().getTrainerId())
                        .name(course.getTrainerProfile().getUser().getName())
                        .title(course.getTitle())
                        .description(course.getDescription())
                        .classDate(course.getDate())
                        .classStartTime(course.getStartTime())
                        .classEndTime(course.getEndTime())
                        .category(course.getCategory())
                        .classroom(course.getRoom())
                        .createdAt(course.getCreatedAt())
                        .updatedAt(course.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, respDtos).getBody();
    }

    @Override
    public ResponseDto<List<CourseResponseDto>> quickSearchCourses(Category category, String trainer, LocalDate classDate, LocalTime classStartTime, LocalTime classEndTime) {
        List<CourseResponseDto> respDtos = null;

        List<Course> filteredCourse = courseRepository.findAll().stream()
                .filter(course -> classDate == null || course.getDate().toLocalDate().equals(classDate))
                .filter(course -> trainer == null || course.getTrainerProfile().getUser().getName().equals(trainer))
                .filter(course -> category == null || course.getCategory().equals(category))
                .filter(course -> classStartTime == null || classStartTime.equals(course.getStartTime()))
                .filter(course -> classEndTime == null || classEndTime.equals(course.getEndTime()))
                .collect(Collectors.toList());

        respDtos = filteredCourse.stream()
                .map(course -> CourseResponseDto.builder()
                        .id(course.getCourseId())
                        .trainerId(course.getTrainerProfile().getTrainerId())
                        .name(course.getTrainerProfile().getUser().getName())
                        .title(course.getTitle())
                        .description(course.getDescription())
                        .classDate(course.getDate())
                        .classStartTime(course.getStartTime())
                        .classEndTime(course.getEndTime())
                        .category(course.getCategory())
                        .classroom(course.getRoom())
                        .createdAt(course.getCreatedAt())
                        .updatedAt(course.getUpdatedAt())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, respDtos).getBody();
    }
}
