package com.example.seniya_back.service.implementations;

import com.example.seniya_back.common.constants.ResponseCode;
import com.example.seniya_back.common.constants.ResponseMessage;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.admin.course.response.CourseResponseDto;
import com.example.seniya_back.dto.admin.user.response.GetAllUserResponseDto;
import com.example.seniya_back.dto.admin.user.response.GetUserDetailRespDto;
import com.example.seniya_back.entity.Course;
import com.example.seniya_back.entity.Participations;
import com.example.seniya_back.entity.User;
import com.example.seniya_back.repository.CourseRepository;
import com.example.seniya_back.repository.ParticipationsRepository;
import com.example.seniya_back.repository.PaymentRepository;
import com.example.seniya_back.repository.UserRepository;
import com.example.seniya_back.service.AdminUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminUserServiceImpl implements AdminUserService {

    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final ParticipationsRepository participationsRepository;
    private final CourseRepository courseRepository;

    @Override
    public ResponseDto<List<GetAllUserResponseDto>> getAllUser() {
        List<GetAllUserResponseDto> respDto = null;

        List<User> users = userRepository.findAll();

        respDto = users.stream().map(user -> GetAllUserResponseDto.builder()
                .id(user.getUserId())
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build()
        ).collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, respDto).getBody();
    }

    @Override
    public ResponseDto<GetUserDetailRespDto> getUserById(long id) {
        GetUserDetailRespDto respDto = null;

        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));

//        List<Participations> participations = participationsRepository.findAllByUserId(user.getUserId());
//
//        List<Course> findCourses = participations.stream()
//                .map(participation -> courseRepository.findById(participation.getCourse().getCourseId())
//                        .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.RESOURCE_NOT_FOUND)))
//                .collect(Collectors.toList());
//
//        List<CourseResponseDto> findCourseRespDtos = findCourses.stream()
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

        respDto = GetUserDetailRespDto.builder()
                .name(user.getName())
                .phone(user.getPhone())
                .roleName(user.getRole().getRoleName())
                .totalAmount(paymentRepository.findTotalAmountByUserId(user.getUserId()))
                .totalCouponCount(paymentRepository.findTotalCouponCountByUserId(user.getUserId()))
                .availableCouponCount(paymentRepository.findAvailableCouponCountByUserId(user.getUserId()))
//                .courses(findCourseRespDtos)
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, respDto).getBody();
    }
}
