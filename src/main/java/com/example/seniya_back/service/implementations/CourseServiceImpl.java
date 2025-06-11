//package com.example.seniya_back.service.implementations;
//
//import com.example.seniya_back.common.constants.ResponseCode;
//import com.example.seniya_back.common.constants.ResponseMessage;
//import com.example.seniya_back.dto.ResponseDto;
//import com.example.seniya_back.dto.admin.course.request.CreateCourseRequestDto;
//import com.example.seniya_back.dto.admin.course.request.UpdateCourseRequestDto;
//import com.example.seniya_back.dto.admin.course.response.GetCourseDetailResponseDto;
//import com.example.seniya_back.dto.admin.course.response.CourseResponseDto;
//import com.example.seniya_back.dto.admin.course.response.UpdateCourseResponseDto;
//import com.example.seniya_back.entity.Course;
//import com.example.seniya_back.entity.TrainerProfile;
//import com.example.seniya_back.repository.CourseRepository;
//import com.example.seniya_back.repository.TrainerProfileRepository;
//import com.example.seniya_back.service.CourseService;
//import jakarta.persistence.EntityNotFoundException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class CourseServiceImpl implements CourseService {
//
//    private final CourseRepository courseRepository;
//    private final TrainerProfileRepository trainerProfileRepository;
//
//    @Override
//    public ResponseDto<CourseResponseDto> createCourse(CreateCourseRequestDto dto) {
//        CourseResponseDto respDto = null;
//
//        TrainerProfile trainerProfile = trainerProfileRepository.findById(dto.getTrainerId()).orElseThrow(() -> new IllegalArgumentException(ResponseMessage.USER_NOT_FOUND));
//
//        Course newCourse = Course.builder()
//                .title(dto.getTitle())
//                .description(dto.getDescription())
//                .category(dto.getCategory())
//                .trainerProfile(trainerProfile)
//                .date(dto.getClassDate())
//                .startTime(dto.getClassStartTime())
//                .endTime(dto.getClassEndTime())
//                .room(dto.getClassroom())
//                .build();
//
//        Course savedCourse = courseRepository.save(newCourse);
//
//        respDto = CourseResponseDto.builder()
//                .name(savedCourse.getTrainerProfile().getUser().getName())
//                .title(savedCourse.getTitle())
//                .description(savedCourse.getDescription())
//                .classDate(savedCourse.getDate())
//                .classStartTime(savedCourse.getStartTime())
//                .classEndTime(savedCourse.getEndTime())
//                .category(savedCourse.getCategory())
//                .classroom(savedCourse.getRoom())
//                .build();
//
//        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, respDto).getBody();
//    }
//
//    @Override
//    public ResponseDto<UpdateCourseResponseDto> updateCourse(Long id, UpdateCourseRequestDto dto) {
//        UpdateCourseResponseDto respDto = null;
//
//        Course course = courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Course not found"));
//
//        if (dto.getTitle() != null) {
//            course.setTitle(dto.getTitle());
//        }
//
//        if (dto.getDescription() != null) {
//            course.setDescription(dto.getDescription());
//        }
//
//        if (dto.getCategory() != null) {
//            course.setCategory(dto.getCategory());
//        }
//
//        if (dto.getTrainerId() != null) {
//            course.setTrainerProfile(course.getTrainerProfile());
//        }
//
//        if (dto.getClassDate() != null) {
//            course.setDate(dto.getClassDate());
//        }
//
//        if (dto.getClassStartTime() != null) {
//            course.setStartTime(dto.getClassStartTime());
//        }
//
//        if (dto.getClassEndTime() != null) {
//            course.setEndTime(dto.getClassEndTime());
//        }
//
//        if (dto.getClassroom() != null) {
//            course.setRoom(dto.getClassroom());
//        }
//
//        Course updatedCourse = courseRepository.save(course);
//
//        respDto = UpdateCourseResponseDto.builder()
//                .name(updatedCourse.getTrainerProfile().getUser().getName())
//                .title(updatedCourse.getTitle())
//                .description(updatedCourse.getDescription())
//                .classDate(updatedCourse.getDate())
//                .classStartTime(updatedCourse.getStartTime())
//                .classEndTime(updatedCourse.getEndTime())
//                .category(updatedCourse.getCategory())
//                .classroom(updatedCourse.getRoom())
//                .build();
//
//        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, respDto).getBody();
//    }
//
//    @Override
//    public ResponseDto<List<CourseResponseDto>> getAllCourses() {
//        List<CourseResponseDto> respDtos = null;
//
//        List<Course> courses = courseRepository.findAll();
//
//        respDtos = courses.stream()
//                .map(course -> CourseResponseDto.builder()
//                        .name(course.getTrainerProfile().getUser().getName())
//                        .title(course.getTitle())
//                        .description(course.getDescription())
//                        .classDate(course.getDate())
//                        .classStartTime(course.getStartTime())
//                        .classEndTime(course.getEndTime())
//                        .category(course.getCategory())
//                        .classroom(course.getRoom())
//                        .build())
//                .collect(Collectors.toList());
//
//        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, respDtos).getBody();
//    }
//
//    @Override
//    public ResponseDto<GetCourseDetailResponseDto> getCourseById(Long id) {
//        GetCourseDetailResponseDto respDto = null;
//
//        Course course = courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Course not found"));
//
//        respDto = GetCourseDetailResponseDto.builder()
//                .trainerId(course.getTrainerProfile().getTrainerId())
//                .trainerName(course.getTrainerProfile().getUser().getName())
//                .title(course.getTitle())
//                .description(course.getDescription())
//                .classDate(course.getDate())
//                .classStartTime(course.getStartTime())
//                .classEndTime(course.getEndTime())
//                .category(course.getCategory())
//                .createdAt(course.getCreatedAt())
//                .updatedAt(course.getUpdatedAt())
//                .classroom(course.getRoom())
//                .build();
//
//        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, respDto).getBody();
//    }
//
//    @Override
//    public ResponseDto<?> deleteCourse(Long id) {
//        Course course = courseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Course not found"));
//
//        courseRepository.delete(course);
//
//        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS).getBody();
//    }
//
//}
