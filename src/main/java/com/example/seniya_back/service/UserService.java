package com.example.seniya_back.service;

import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.user.request.MyInfoUpdReqDto;
import com.example.seniya_back.dto.user.request.UserSignInRequestDto;
import com.example.seniya_back.dto.user.response.GetMyInfoResDto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


public interface UserService {
    ResponseDto<GetMyInfoResDto> getUserInfo(String userEmail);
    ResponseDto<Void> deleteUser(String userEmail);
    ResponseDto<GetMyInfoResDto> updateUserInfo(String userEmail, @Valid MyInfoUpdReqDto dto);

    @Transactional
    void signIn(UserSignInRequestDto requestDto);
}
