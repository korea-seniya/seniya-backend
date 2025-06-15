package com.example.seniya_back.dto.healthdata.allergy.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AllergyResponseDto {
    private Long allergyId;
    private String allergyName;
    private String reaction;
}
