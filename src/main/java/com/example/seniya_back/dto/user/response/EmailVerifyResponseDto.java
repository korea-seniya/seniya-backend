package com.example.seniya_back.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmailVerifyResponseDto {
    private boolean verified;
    private String message;
}