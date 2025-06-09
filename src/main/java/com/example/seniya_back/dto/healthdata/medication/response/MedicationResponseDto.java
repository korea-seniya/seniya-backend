package com.example.seniya_back.dto.healthdata.medication.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MedicationResponseDto {
    private Long medicationId;
    private String medicationName;
}
