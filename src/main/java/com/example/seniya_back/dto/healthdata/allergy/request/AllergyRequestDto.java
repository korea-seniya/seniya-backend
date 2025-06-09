package com.example.seniya_back.dto.healthdata.allergy.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllergyRequestDto {
    private String allergyName;
    private String reaction;
}