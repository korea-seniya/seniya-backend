package com.example.seniya_back.dto.healthdata.medication.response;

import com.example.seniya_back.entity.Medication;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MedicationResponseDto {
    private Long medicationId;
    private String medicationName;

    public static MedicationResponseDto from(Medication medication) {
        return MedicationResponseDto.builder()
                .medicationId(medication.getMedicationId())
                .medicationName(medication.getMedicationName())
                .build();
    }

}
