package com.example.seniya_back.service.implementations;

import com.example.seniya_back.common.constants.ResponseCode;
import com.example.seniya_back.common.constants.ResponseMessage;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.admin.user.response.GetAllUserResponseDto;
import com.example.seniya_back.dto.admin.user.response.GetUserCourseResponseDto;
import com.example.seniya_back.dto.admin.user.response.GetUserDetailFlatRow;
import com.example.seniya_back.dto.admin.user.response.GetUserDetailResponseDto;
import com.example.seniya_back.entity.User;
import com.example.seniya_back.mapper.UserDetailMapper;
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
    private final UserDetailMapper userDetailMapper;

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
    public ResponseDto<GetUserDetailResponseDto> getUserDetail(long userId) {
        List<GetUserDetailFlatRow> rows = userDetailMapper.getUserDetail(userId);

        if (rows.isEmpty()) {
            throw new EntityNotFoundException("해당 유저가 존재하지 않습니다.");
        }

        GetUserDetailFlatRow first = rows.get(0);

        List<GetUserCourseResponseDto> courses = rows.stream()
                .filter(row -> row.getCourseId() != null)
                .map(row -> GetUserCourseResponseDto.builder()
                        .courseId(row.getCourseId())
                        .title(row.getTitle())
                        .description(row.getDescription())
                        .courseDate(row.getCourseDate())
                        .courseStartTime(row.getCourseStartTime())
                        .courseEndTime(row.getCourseEndTime())
                        .category(row.getCategory())
                        .courseRoom(row.getCourseRoom())
                        .courseCreatedAt(row.getCourseCreatedAt())
                        .courseUpdatedAt(row.getCourseUpdatedAt())
                        .trainerId(row.getTrainerId())
                        .trainerName(row.getTrainerName())
                        .build())
                .toList();

        GetUserDetailResponseDto result = GetUserDetailResponseDto.builder()
                .userName(first.getUserName())
                .phone(first.getPhone())
                .roleName(first.getRoleName())
                .totalAmount(first.getTotalAmount())
                .totalCouponCount(first.getTotalCouponCount())
                .availableCouponCount(first.getAvailableCouponCount())
                .courses(courses)
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, result).getBody();
    }



}
