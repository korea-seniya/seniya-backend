package com.example.seniya_back.service.implementations;

import com.example.seniya_back.common.constants.ResponseCode;
import com.example.seniya_back.common.constants.ResponseMessage;
import com.example.seniya_back.common.enums.Category;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.course.response.CourseApplyResponseDto;
import com.example.seniya_back.dto.course.response.CourseDetailResponseDto;
import com.example.seniya_back.dto.course.response.CourseListResponseDto;
import com.example.seniya_back.entity.Course;
import com.example.seniya_back.entity.Participations;
import com.example.seniya_back.entity.User;
import com.example.seniya_back.repository.CourseRepository;
import com.example.seniya_back.repository.ParticipationsRepository;
import com.example.seniya_back.repository.UserRepository;
import com.example.seniya_back.service.UserCourseService;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserCourseServiceImpl implements UserCourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ParticipationsRepository participationsRepository;

    @Override
    public ResponseDto<List<CourseListResponseDto>> getAllCourses() {
        List<CourseListResponseDto> dto = null;

        List<Course> courses = courseRepository.findAll();

        dto = courses.stream()
                .map(course -> CourseListResponseDto.builder()
                        .courseId(course.getCourseId())
                        .name(course.getTrainerProfile().getUser().getName())
                        .title(course.getTitle())
                        .description(course.getDescription())
                        .classDate(course.getDate())
                        .classStartTime(course.getStartTime())
                        .classEndTime(course.getEndTime())
                        .classroom(course.getRoom())
                        .category(course.getCategory())
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
                .courseId(course.getCourseId())
                .trainerId(course.getTrainerProfile().getTrainerId())
                .trainerName(course.getTrainerProfile().getUser().getName())
                .title(course.getTitle())
                .description(course.getDescription())
                .classDate(course.getDate())
                .classStartTime(course.getStartTime())
                .classEndTime(course.getEndTime())
                .classroom(course.getRoom())
                .category(course.getCategory())
                .createdAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, dto).getBody();
    }

    @Override
    public ResponseDto<List<CourseListResponseDto>> getCoursesByCategory(Category category) {
        List<Course> courses;

        if (category == null) {
            courses = courseRepository.findAll();
        } else {
            courses = courseRepository.findByCategory(category);
        }

        if (courses.isEmpty()) {
            throw new EntityNotFoundException("해당 카테고리의 강의를 찾을 수 없습니다.");
        }

        List<CourseListResponseDto> dto = courses.stream()
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

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, dto).getBody();
    }

    @Override
    public ResponseDto<List<CourseListResponseDto>> getCoursesByTrainerName(String trainerName) {
        List<Course> courses = courseRepository.findByTrainerProfile_User_Name(trainerName);

        if (courses.isEmpty()) {
            throw new EntityNotFoundException("해당 트레이너의 강의를 찾을 수 없습니다.");
        }

        List<CourseListResponseDto> dto = courses.stream()
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

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, dto).getBody();
    }

    @Override
    public ResponseDto<CourseApplyResponseDto> applyCourse(String username, Long id) {
        CourseApplyResponseDto responseDto = null;

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.RESOURCE_NOT_FOUND));

        if (participationsRepository.existsByUserAndCourse(user, course)){
            throw new DuplicateRequestException(ResponseMessage.FAILED);
        }

        Participations participation = Participations.builder()
                .user(user)
                .course(course)
                .build();
        participationsRepository.save(participation);

        responseDto = CourseApplyResponseDto.builder()
                .courseId(course.getCourseId())
                .userId(user.getUserId())
                .userName(user.getName())
                .trainerId(course.getTrainerProfile().getTrainerId())
                .trainerName(course.getTrainerProfile().getUser().getName())
                .title(course.getTitle())
                .date(course.getDate())
                .startTime(course.getStartTime())
                .endTime(course.getEndTime())
                .room(course.getRoom())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }
}
