package com.example.seniya_back.dto.user.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyInfoUpdateRequestDto {
    private String userName;
    private String email;
    private String phone;
}
