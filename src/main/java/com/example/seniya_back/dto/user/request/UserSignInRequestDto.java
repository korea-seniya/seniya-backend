package com.example.seniya_back.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignInRequestDto { // 로그인

    @NotBlank(message = "아이디는 필수 입력 값 입니다.")
    private String userName;

    @NotBlank(message = "비밀번호는 필수 입력 값 입니다.")
    private String password;
}