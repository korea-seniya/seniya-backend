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

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    // 이메일 인증 완료 처리 - email_verified 컬럼 true로 변경
    Mono<Void> completeEmailVerification(String email);

    // 토큰 검증 후 이메일 추출 및 인증 여부 체크
    Mono<String> verifyResetPasswordToken(String token);

    // 이메일로 비밀번호 재설정
    Mono<ResponseEntity<String>> resetPassword(String email, String newPassword);

    // 필요시 기존 메서드
    Mono<ResponseEntity<String>> resetPassword(@Valid UserPasswordResetRequestDto dto);

}
