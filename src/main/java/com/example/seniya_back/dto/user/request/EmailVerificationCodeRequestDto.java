package com.example.seniya_back.dto.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailVerificationCodeRequestDto { // 이메일 인증 코드 전송
    @NotBlank(message = "아이디는 필수 입력 값 입니다.")
    private String userName;

    @Email(message = "올바른 이메일 형식이어야 합니다.")
    @NotBlank(message = "이메일은 필수 입력 값 입니다.")
    private String email;
}