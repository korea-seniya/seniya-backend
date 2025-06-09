package com.example.seniya_back.service;

import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.healthdata.request.HealthDataRequestDto;
import com.example.seniya_back.dto.healthdata.request.HealthDataUpdRequestDto;
import com.example.seniya_back.dto.healthdata.response.HealthDataResponseDto;
import jakarta.validation.Valid;

public interface HealthDataService {
    ResponseDto<HealthDataResponseDto> createHealthData(@Valid HealthDataRequestDto dto);

    ResponseDto<HealthDataResponseDto> updateHealthData(Long id, @Valid HealthDataUpdRequestDto dto);

    ResponseDto<HealthDataResponseDto> getHealthDataById(Long id);
}
