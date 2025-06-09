package com.example.seniya_back.service;

import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.user.request.MyInfoUpdateRequestDto;
import com.example.seniya_back.dto.user.request.UserSignInRequestDto;
import com.example.seniya_back.dto.user.response.GetMyInfoResponseDto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


public interface UserService {
    ResponseDto<GetMyInfoResponseDto> getUserInfo(String email);
    ResponseDto<Void> deleteUser(String userEmail);
    ResponseDto<GetMyInfoResponseDto> updateUserInfo(String email, @Valid MyInfoUpdateRequestDto dto);

    @Transactional
    void signIn(UserSignInRequestDto requestDto);
}
