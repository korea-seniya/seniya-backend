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
import com.example.seniya_back.repository.AllergyRepository;
import com.example.seniya_back.repository.DiseaseRepository;
import com.example.seniya_back.repository.HealthDateRepository;
import com.example.seniya_back.repository.MedicationRepository;
import com.example.seniya_back.service.HealthDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HealthDataServiceImpl implements HealthDataService {
    private final HealthDateRepository healthDateRepository;
    private final MedicationRepository medicationRepository;
    private final AllergyRepository allergyRepository;
    private final DiseaseRepository diseaseRepository;

    @Override
    @Transactional
    public ResponseDto<HealthDataResponseDto> createHealthData(HealthDataRequestDto dto) {
        HealthData newHealthData = HealthData.builder()
                .height(dto.getHeight())
                .weight(dto.getWeight())
                .bloodPressure(dto.getBloodPressure())
                .smoking(dto.getSmoking())
                .bodyFatPercentage(dto.getBodyFatPercentage())
                .build();

        healthDateRepository.save(newHealthData);

        List<Disease> diseases = Optional.ofNullable(dto.getDiseases())
                .orElse(Collections.emptyList())
                .stream()
                .map(diseaseDto -> Disease.builder()
                        .diseaseName(diseaseDto.getDiseaseName())
                        .diseaseDate(diseaseDto.getDiseaseDate())
                        .diseaseStatus(diseaseDto.getDiseaseStatus())
                        .healthData(newHealthData)
                        .build())
                .collect(Collectors.toList());

        diseaseRepository.saveAll(diseases);

        List<Medication> medications = Optional.ofNullable(dto.getMedications())
                .orElse(Collections.emptyList())
                .stream()
                .map(medicationDto -> Medication.builder()
                        .medicationName(medicationDto.getMedicationName())
                        .healthData(newHealthData)
                        .build())
                .collect(Collectors.toList());

        medicationRepository.saveAll(medications);

        List<Allergy> allergies = Optional.ofNullable(dto.getAllergies())
                .orElse(Collections.emptyList())
                .stream()
                .map(allergyDto -> Allergy.builder()
                        .allergyName(allergyDto.getAllergyName())
                        .reaction(allergyDto.getReaction())
                        .healthData(newHealthData)
                        .build())
                .collect(Collectors.toList());

        allergyRepository.saveAll(allergies);

        HealthDataResponseDto responseDto = HealthDataResponseDto.builder()
                .healthDataId(newHealthData.getHealthDataId())
                .height(newHealthData.getHeight())
                .weight(newHealthData.getWeight())
                .bodyFatPercentage(newHealthData.getBodyFatPercentage())
                .bloodPressure(newHealthData.getBloodPressure())
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
