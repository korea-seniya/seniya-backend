package com.example.seniya_back.dto.Inquiry.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InquiryAnswerReqDto {
    @NotBlank
    private String response;
}
