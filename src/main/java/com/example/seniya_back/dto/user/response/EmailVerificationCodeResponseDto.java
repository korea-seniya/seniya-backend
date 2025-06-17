package com.example.seniya_back.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class EmailVerificationCodeResponseDto { // 이메일 인증 코드 전송
    private String message;
}
