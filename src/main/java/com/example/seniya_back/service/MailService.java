package com.example.seniya_back.service;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface MailService {
    /**
     * 인증 메일 전송
     */
    Mono<ResponseEntity<String>> sendSimpleMessage(@Email(message = "유효한 이메일 주소를 입력해주세요.") @NotBlank(message = "이메일은 필수입니다.") String email);
    /**
     * 인증 토큰 검증
     */
    Mono<ResponseEntity<String>> verifyEmail(String token);
}