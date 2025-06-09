package com.example.seniya_back.dto.healthdata.request;

import com.example.seniya_back.common.enums.BloodPressure;
import com.example.seniya_back.dto.healthdata.allergy.request.AllergyRequestDto;
import com.example.seniya_back.dto.healthdata.disease.requset.DiseaseRequestDto;
import com.example.seniya_back.dto.healthdata.medication.request.MedicationRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthDataRequestDto {
    private Float height;
    private Float weight;
    private Float bodyFatPercentage;
    private BloodPressure bloodPressure;

    private List<DiseaseRequestDto> diseases;
    private List<MedicationRequestDto> medications;
    private List<AllergyRequestDto> allergies;

    private Boolean smoking;
    private Boolean drinking;
}
