package com.example.seniya_back.dto.healthdata.response;

import com.example.seniya_back.common.enums.BloodPressure;
import com.example.seniya_back.dto.healthdata.allergy.response.AllergyResponseDto;
import com.example.seniya_back.dto.healthdata.disease.response.DiseaseResponseDto;
import com.example.seniya_back.dto.healthdata.medication.response.MedicationResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class HealthDataResponseDto {
    private Long healthDataId;
    private Float height;
    private Float weight;
    private Float bodyFatPercentage;
    private BloodPressure bloodPressure;
    private Boolean smoking;
    private Boolean drinking;

    private List<DiseaseResponseDto> diseases;
    private List<MedicationResponseDto> medications;
    private List<AllergyResponseDto> allergies;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
