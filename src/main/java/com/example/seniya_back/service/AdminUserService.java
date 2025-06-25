package com.example.seniya_back.service;

import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.admin.user.response.GetAllUserResponseDto;
import com.example.seniya_back.dto.admin.user.response.GetUserDetailResponseDto;

import java.util.List;

public interface AdminUserService {
    ResponseDto<List<GetAllUserResponseDto>> getAllUser();

    ResponseDto<GetUserDetailResponseDto> getUserDetail(long userId);
}
