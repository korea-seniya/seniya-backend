package com.example.seniya_back.dto.Inquiry.requestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InquiryRequestDto {
    private String title;
    private String content;
    private Boolean isPrivated;
}