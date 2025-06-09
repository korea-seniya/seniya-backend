package com.example.seniya_back.service.implementations;

import com.example.seniya_back.common.constants.ResponseCode;
import com.example.seniya_back.common.constants.ResponseMessage;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.healthdata.request.HealthDataRequestDto;
import com.example.seniya_back.dto.healthdata.request.HealthDataUpdRequestDto;
import com.example.seniya_back.dto.healthdata.response.HealthDataResponseDto;
import com.example.seniya_back.entity.Allergy;
import com.example.seniya_back.entity.Disease;
import com.example.seniya_back.entity.HealthData;
import com.example.seniya_back.entity.Medication;
import com.example.seniya_back.repository.HealthDateRepository;
import com.example.seniya_back.service.HealthDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HealthDataServiceImpl implements HealthDataService {
    private final HealthDateRepository healthDateRepository;

    @Override
    @Transactional
    public ResponseDto<HealthDataResponseDto> createHealthData(HealthDataRequestDto dto) {
        HealthDataResponseDto responseDto = null;

        HealthData newHealthData = HealthData.builder()
                .height(dto.getHeight())
                .weight(dto.getWeight())
                .bloodPressure(dto.getBloodPressure())
                .smoking(dto.getSmoking())
                .bodyFatPercentage(dto.getBodyFatPercentage())
                .build();

        healthDateRepository.save(newHealthData);

        List<Disease> diseases = dto.getDiseases().stream()
                .map(diseaseDto -> Disease.builder()
                        .diseaseName(diseaseDto.getDiseaseName())
                        .diseaseDate(diseaseDto.getDiseaseDate())
                        .diseaseStatus(diseaseDto.getDiseaseStatus())
                        .healthData(newHealthData)
                        .build())
                .collect(Collectors.toList());

        List<Medication> medications = dto.getMedications().stream()
                .map(medicationDto -> Medication.builder()
                        .medicationName(medicationDto.getMedicationName())
                        .healthData(newHealthData)
                        .build())
                .collect(Collectors.toList());

        List<Allergy> allergies = dto.getAllergies().stream()
                .map(allergyDto -> Allergy.builder()
                        .allergyName(allergyDto.getAllergyName())
                        .reaction(allergyDto.getReaction())
                        .healthData(newHealthData)
                        .build())
                .collect(Collectors.toList());

        responseDto = HealthDataResponseDto.builder()
                .healthDataId(newHealthData.getHealthDataId())
                .height(newHealthData.getHeight())
                .weight(newHealthData.getWeight())
                .bodyFatPercentage(newHealthData.getBodyFatPercentage())
                .bloodPressure(newHealthData.getBloodPressure())
                .diseaseName(diseases.isEmpty() ? null : diseases.get(0).getDiseaseName())
                .diseaseDate(diseases.isEmpty() ? null : diseases.get(0).getDiseaseDate())
                .diseaseStatus(diseases.isEmpty() ? null : diseases.get(0).getDiseaseStatus())
                .medicationName(medications.isEmpty() ? null : medications.get(0).getMedicationName())
                .allergyName(allergies.isEmpty() ? null : allergies.get(0).getAllergyName())
                .reaction(allergies.isEmpty() ? null : allergies.get(0).getReaction())
                .createdAt(newHealthData.getCreatedAt())
                .updatedAt(newHealthData.getUpdatedAt())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }

    @Override
    public ResponseDto<HealthDataResponseDto> updateHealthData(Long id, HealthDataUpdRequestDto dto) {
        HealthDataResponseDto responseDto = null;

        HealthData healthData = healthDateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.FILE_NOT_FOUND));

        healthData.setHeight(dto.getHeight());
        healthData.setWeight(dto.getWeight());
        healthData.setBloodPressure(dto.getBloodPressure());
        healthData.setBodyFatPercentage(dto.getBodyFatPercentage());
        healthData.setDrinking(dto.getDrinking());
        healthData.setSmoking(dto.getSmoking());

        HealthData updatedHealthData = healthDateRepository.save(healthData);

//        responseDto = HealthDataResponseDto.builder()
//                .healthDataId(updatedHealthData.getHealthDataId())
//                .height(updatedHealthData.getHeight())
//                .weight(updatedHealthData.getWeight())
//                .bloodPressure(updatedHealthData.getBloodPressure())
//                .bodyFatPercentage(updatedHealthData.getBodyFatPercentage())
//                .
//                .build();



        return null;
    }

    @Override
    public ResponseDto<HealthDataResponseDto> getHealthDataById(Long id) {
        return null;
    }
}
