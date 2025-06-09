package com.example.seniya_back.dto.healthdata.disease.response;

import com.example.seniya_back.common.enums.DiseaseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class DiseaseResponseDto {
    private Long diseaseId;
    private String diseaseName;
    private LocalDate diseaseDate;
    private DiseaseStatus diseaseStatus;
}
