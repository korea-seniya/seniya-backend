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
public class GetMyInfoResponseDto {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public GetMyInfoResponseDto(Long id, String username, String email, String phone) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
    }
}
