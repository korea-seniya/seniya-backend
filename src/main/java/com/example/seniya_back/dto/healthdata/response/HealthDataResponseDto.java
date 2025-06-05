package com.example.seniya_back.dto.healthdata.response;

import com.example.seniya_back.common.enums.BloodPressure;
import com.example.seniya_back.common.enums.DiseaseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class HealthDataResponseDto {
    private Float height;
    private Float weight;
    private Float bodyFatPercentage;
    private BloodPressure bloodPressure;

    private String diseaseName;
    private LocalDate diseaseDate;
    private DiseaseStatus diseaseStatus;

    private String allergyName;
    private String reaction;

    private String medicationName;

    private LocalDate createdAt;
    private LocalDate updatedAt;
}
