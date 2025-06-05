package com.example.seniya_back.dto.Inquiry.responseDto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyInquiryResponseDto {
    private String title;
    private String content;
    private String response;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
