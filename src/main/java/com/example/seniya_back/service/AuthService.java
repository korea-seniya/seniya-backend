package com.example.seniya_back.service;

import reactor.core.publisher.Mono;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.user.request.UserPasswordResetRequestDto;
import com.example.seniya_back.dto.user.request.UserSignInRequestDto;
import com.example.seniya_back.dto.user.request.UserSignUpRequestDto;
import com.example.seniya_back.dto.user.response.UserSignInResponseDto;
import com.example.seniya_back.dto.user.response.UserSignUpResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseDto<UserSignUpResponseDto> signup(UserSignUpRequestDto dto);

    ResponseDto<UserSignInResponseDto> login(UserSignInRequestDto dto);

    ResponseDto<?> logout(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Mono<Void> completeEmailVerification(String email);

    Mono<String> verifyResetPasswordToken(String token);

    Mono<ResponseEntity<String>> resetPassword(String email, String newPassword);

    Mono<ResponseEntity<String>> resetPassword(UserPasswordResetRequestDto dto);

    boolean isUsernameAvailable(String username);

    boolean isEmailVerified(String email);
}
