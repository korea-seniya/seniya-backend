package com.example.seniya_back.service.implementations;

import com.example.seniya_back.common.constants.ResponseCode;
import com.example.seniya_back.common.constants.ResponseMessage;
import com.example.seniya_back.common.enums.Category;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.course.response.CourseDetailResponseDto;
import com.example.seniya_back.dto.course.response.CourseListResponseDto;
import com.example.seniya_back.entity.Course;
import com.example.seniya_back.repository.CourseRepository;
import com.example.seniya_back.service.UserCourseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserCourseServiceImpl implements UserCourseService {
    private final CourseRepository courseRepository;

    @Override
    public ResponseDto<List<CourseListResponseDto>> getAllCourses() {
        List<CourseListResponseDto> dto = null;

        List<Course> courses = courseRepository.findAll();

        dto = courses.stream()
                .map(course -> CourseListResponseDto.builder()
                        .name(course.getTrainerProfile().getUser().getName())
                        .title(course.getTitle())
                        .description(course.getDescription())
                        .classDate(course.getDate())
                        .classStartTime(course.getStartTime())
                        .classEndTime(course.getEndTime())
                        .classroom(course.getRoom())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS,dto).getBody();
    }

    @Override
    public ResponseDto<CourseDetailResponseDto> getCourseById(Long id) {
        CourseDetailResponseDto dto = null;

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.FILE_NOT_FOUND));

        dto = CourseDetailResponseDto.builder()
                .trainerId(course.getTrainerProfile().getTrainerId())
                .trainerName(course.getTrainerProfile().getUser().getName())
                .title(course.getTitle())
                .description(course.getDescription())
                .classDate(course.getDate())
                .classStartTime(course.getStartTime())
                .classEndTime(course.getEndTime())
                .classroom(course.getRoom())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, dto).getBody();
    }

    @Override
    public ResponseDto<List<CourseListResponseDto>> getCoursesByCategory(Category category) {
        List<CourseListResponseDto> dto = null;

        List<Course> courses = courseRepository.findByCategory(category);

        dto = courses.stream()
                .map(course -> CourseListResponseDto.builder()
                        .courseId(course.getCourseId())
                        .name(course.getTrainerProfile().getUser().getName())
                        .title(course.getTitle())
                        .description(course.getDescription())
                        .classDate(course.getDate())
                        .classStartTime(course.getStartTime())
                        .classEndTime(course.getEndTime())
                        .category(course.getCategory())
                        .classroom(course.getRoom())
                        .build())
                .collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS,dto).getBody();
    }


}
