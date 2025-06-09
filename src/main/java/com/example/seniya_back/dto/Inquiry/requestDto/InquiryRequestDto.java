package com.example.seniya_back.dto.Inquiry.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InquiryRequestDto {
    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
