package com.example.seniya_back.dto.user.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String roleName;
    private String name;
}
