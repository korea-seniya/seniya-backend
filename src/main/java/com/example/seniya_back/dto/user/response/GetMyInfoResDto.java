package com.example.seniya_back.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetMyInfoResDto {
    private Long id;
    private String userName;
    private String email;
    private String phone;
    private LocalDate createdAt;
}
