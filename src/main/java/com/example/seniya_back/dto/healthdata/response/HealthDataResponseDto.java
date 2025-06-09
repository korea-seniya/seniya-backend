package com.example.seniya_back.dto.healthdata.response;

import com.example.seniya_back.common.enums.BloodPressure;
import com.example.seniya_back.common.enums.DiseaseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class HealthDataResponseDto {
    private Long healthDataId;
    private Float height;
    private Float weight;
    private Float bodyFatPercentage;
    private BloodPressure bloodPressure;

    private Long diseaseId;
    private String diseaseName;
    private LocalDate diseaseDate;
    private DiseaseStatus diseaseStatus;

    private Long allergyId;
    private String allergyName;
    private String reaction;

    private Long medicineId;
    private String medicationName;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
