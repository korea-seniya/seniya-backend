package com.example.seniya_back.service;

import reactor.core.publisher.Mono;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.user.request.UserPasswordResetRequestDto;
import com.example.seniya_back.dto.user.request.UserSignInRequestDto;
import com.example.seniya_back.dto.user.request.UserSignUpRequestDto;
import com.example.seniya_back.dto.user.response.UserSignInResponseDto;
import com.example.seniya_back.dto.user.response.UserSignUpResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseDto<UserSignUpResponseDto> signup(@Valid UserSignUpRequestDto dto);
    ResponseDto<UserSignInResponseDto> login(@Valid UserSignInRequestDto dto);

    ResponseDto<?> logout(String username);

    Mono<ResponseEntity<String>> resetPassword(UserPasswordResetRequestDto dto);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

//    Mono<ResponseEntity<String>> resetPassword(@Valid UserPasswordResetRequestDto dto);
}
