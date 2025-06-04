package com.example.seniya_back.dto.healthdata.request;

import com.example.seniya_back.common.enums.BloodPressure;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HealthDataReqDto {
    private Float height;
    private Float weight;
    private Float bodyFatPercentage;
    private BloodPressure bloodPressure;

    private Long diseaseId;
    private Long medicationId;
    private Long allergyId;

    private Boolean smoking;
    private Boolean drinking;
}
