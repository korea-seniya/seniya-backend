package com.example.seniya_back.dto.healthdata.disease.requset;

import com.example.seniya_back.common.enums.DiseaseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiseaseRequestDto {
    private String diseaseName;
    private LocalDate diseaseDate;
    private DiseaseStatus diseaseStatus;
}
