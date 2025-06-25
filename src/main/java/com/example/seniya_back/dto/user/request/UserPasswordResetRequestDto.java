package com.example.seniya_back.dto.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordResetRequestDto { // 비밀번호 재설정

    @Email(message = "올바른 이메일 형식이어야 합니다.")
    @NotBlank(message = "이메일은 필수 입력 값 입니다.")
    private String email;

    @NotBlank(message = "토큰은 필수 입력 값 입니다.")
    private String token;

    @NotBlank(message = "새로운 비밀번호는 필수 입력 값 입니다.")
    private String newPassword;
}