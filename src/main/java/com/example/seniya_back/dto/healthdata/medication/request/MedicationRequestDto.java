package com.example.seniya_back.dto.healthdata.medication.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicationRequestDto {
    private String medicationName;
}
