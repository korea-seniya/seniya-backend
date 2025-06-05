package com.example.seniya_back.dto.Inquiry.responseDto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InquiryByIdResponseDto {
    private String title;
    private String userName;
    private String trainerName;
    private String content;
    private String response;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
