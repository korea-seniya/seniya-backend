package com.example.seniya_back.service.implementations;

import com.example.seniya_back.common.constants.ResponseCode;
import com.example.seniya_back.common.constants.ResponseMessage;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.healthdata.allergy.response.AllergyResponseDto;
import com.example.seniya_back.dto.healthdata.disease.response.DiseaseResponseDto;
import com.example.seniya_back.dto.healthdata.medication.response.MedicationResponseDto;
import com.example.seniya_back.dto.healthdata.request.HealthDataRequestDto;
import com.example.seniya_back.dto.healthdata.request.HealthDataUpdRequestDto;
import com.example.seniya_back.dto.healthdata.response.HealthDataResponseDto;
import com.example.seniya_back.entity.*;
import com.example.seniya_back.repository.*;
import com.example.seniya_back.service.HealthDataService;
import jakarta.persistence.EntityNotFoundException;
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
    private final UserRepository userRepository;
    private final HealthDateRepository healthDateRepository;
    private final MedicationRepository medicationRepository;
    private final AllergyRepository allergyRepository;
    private final DiseaseRepository diseaseRepository;

    @Override
    @Transactional
    public ResponseDto<HealthDataResponseDto> createHealthData(String username, HealthDataRequestDto dto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));

        if (healthDateRepository.existsByUser(user)) {
            throw new IllegalArgumentException("이미 등록된 건강 데이터가 존재합니다.");
        }

        HealthData newHealthData = HealthData.builder()
                .user(user)
                .height(dto.getHeight())
                .weight(dto.getWeight())
                .bloodPressure(dto.getBloodPressure())
                .smoking(dto.getSmoking())
                .drinking(dto.getDrinking())
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
                .diseases(diseases.stream()
                        .map(DiseaseResponseDto::from)
                        .toList())
                .medications(medications.stream()
                        .map(MedicationResponseDto::from)
                        .toList())
                .allergies(allergies.stream()
                        .map(AllergyResponseDto::from)
                        .toList())
                .createdAt(newHealthData.getCreatedAt())
                .updatedAt(newHealthData.getUpdatedAt())
                .build();
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }

    @Override
    @Transactional
    public ResponseDto<HealthDataResponseDto> updateHealthData(String username, HealthDataUpdRequestDto dto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));

        HealthData healthData = healthDateRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.FILE_NOT_FOUND));

        if (!healthData.getUser().getUsername().equals(username)) {
            throw new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND);
        }

        if (dto.getHeight() != null) {
            healthData.setHeight(dto.getHeight());
        }
        if (dto.getWeight() != null) {
            healthData.setWeight(dto.getWeight());
        }
        if (dto.getBloodPressure() != null) {
            healthData.setBloodPressure(dto.getBloodPressure());
        }
        if (dto.getBodyFatPercentage() != null) {
            healthData.setBodyFatPercentage(dto.getBodyFatPercentage());
        }
        if (dto.getDrinking() != null) {
            healthData.setDrinking(dto.getDrinking());
        }
        if (dto.getSmoking() != null) {
            healthData.setSmoking(dto.getSmoking());
        }

        List<Disease> existingDiseases = diseaseRepository.findAllByHealthData(healthData);
        List<Medication> existingMedications = medicationRepository.findAllByHealthData(healthData);
        List<Allergy> existingAllergies = allergyRepository.findAllByHealthData(healthData);

        if (dto.getDiseases() != null) {
            existingDiseases.stream()
                    .filter(ed -> dto.getDiseases().stream()
                            .noneMatch(d -> d.getDiseaseName().equals(ed.getDiseaseName())
                                    && d.getDiseaseDate().equals(ed.getDiseaseDate())
                                    && d.getDiseaseStatus().equals(ed.getDiseaseStatus())))
                    .forEach(diseaseRepository::delete);

            List<Disease> newDiseases = dto.getDiseases().stream()
                    .filter(d -> existingDiseases.stream().noneMatch(ed ->
                            ed.getDiseaseName().equals(d.getDiseaseName()) &&
                                    ed.getDiseaseDate().equals(d.getDiseaseDate()) &&
                                    ed.getDiseaseStatus().equals(d.getDiseaseStatus())
                    ))
                    .map(d -> Disease.builder()
                            .diseaseName(d.getDiseaseName())
                            .diseaseDate(d.getDiseaseDate())
                            .diseaseStatus(d.getDiseaseStatus())
                            .healthData(healthData)
                            .build())
                    .collect(Collectors.toList());
            diseaseRepository.saveAll(newDiseases);
        }

        if (dto.getMedications() != null) {
            existingMedications.stream()
                    .filter(em -> dto.getMedications().stream()
                            .noneMatch(m -> m.getMedicationName().equals(em.getMedicationName())))
                    .forEach(medicationRepository::delete);

            List<Medication> newMedications = dto.getMedications().stream()
                    .filter(m -> existingMedications.stream().noneMatch(em ->
                            em.getMedicationName().equals(m.getMedicationName())))
                    .map(m -> Medication.builder()
                            .medicationName(m.getMedicationName())
                            .healthData(healthData)
                            .build())
                    .collect(Collectors.toList());
            medicationRepository.saveAll(newMedications);
        }

        if (dto.getAllergies() != null) {
            existingAllergies.stream()
                    .filter(ea -> dto.getAllergies().stream()
                            .noneMatch(a -> a.getAllergyName().equals(ea.getAllergyName())
                                    && a.getReaction().equals(ea.getReaction())))
                    .forEach(allergyRepository::delete);

            List<Allergy> newAllergies = dto.getAllergies().stream()
                    .filter(a -> existingAllergies.stream().noneMatch(ea ->
                            ea.getAllergyName().equals(a.getAllergyName()) &&
                                    ea.getReaction().equals(a.getReaction())))
                    .map(a -> Allergy.builder()
                            .allergyName(a.getAllergyName())
                            .reaction(a.getReaction())
                            .healthData(healthData)
                            .build())
                    .collect(Collectors.toList());
            allergyRepository.saveAll(newAllergies);
        }

        healthDateRepository.save(healthData);

        HealthDataResponseDto responseDto = HealthDataResponseDto.builder()
                .healthDataId(healthData.getHealthDataId())
                .height(healthData.getHeight())
                .weight(healthData.getWeight())
                .bodyFatPercentage(healthData.getBodyFatPercentage())
                .bloodPressure(healthData.getBloodPressure())
                .smoking(healthData.getSmoking())
                .drinking(healthData.getDrinking())
                .diseases(diseaseRepository.findAllByHealthData(healthData).stream().map(DiseaseResponseDto::from).toList())
                .medications(medicationRepository.findAllByHealthData(healthData).stream().map(MedicationResponseDto::from).toList())
                .allergies(allergyRepository.findAllByHealthData(healthData).stream().map(AllergyResponseDto::from).toList())
                .createdAt(healthData.getCreatedAt())
                .updatedAt(healthData.getUpdatedAt())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }

    @Override
    public ResponseDto<HealthDataResponseDto> getHealthDataByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));

        // 사용자 기준으로 HealthData 가져오기
        HealthData healthData = healthDateRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.FILE_NOT_FOUND));

        HealthDataResponseDto responseDto = HealthDataResponseDto.builder()
                .healthDataId(healthData.getHealthDataId())
                .height(healthData.getHeight())
                .weight(healthData.getWeight())
                .bodyFatPercentage(healthData.getBodyFatPercentage())
                .bloodPressure(healthData.getBloodPressure())
                .smoking(healthData.getSmoking())
                .drinking(healthData.getDrinking())
                .diseases(healthData.getDisease().stream()
                        .map(DiseaseResponseDto::from)
                        .toList())
                .medications(healthData.getMedication().stream()
                        .map(MedicationResponseDto::from)
                        .toList())
                .allergies(healthData.getAllergy().stream()
                        .map(AllergyResponseDto::from)
                        .toList())
                .createdAt(healthData.getCreatedAt())
                .updatedAt(healthData.getUpdatedAt())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }


//    @Override
//    public ResponseDto<HealthDataResponseDto> getHealthDataById(String username, Long id) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));
//
//        HealthData healthData = healthDateRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.FILE_NOT_FOUND));
//
//        if (!healthData.getUser().getUsername().equals(username)) {
//            throw new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND);
//        }
//
//        HealthDataResponseDto responseDto = HealthDataResponseDto.builder()
//                .healthDataId(healthData.getHealthDataId())
//                .height(healthData.getHeight())
//                .weight(healthData.getWeight())
//                .bodyFatPercentage(healthData.getBodyFatPercentage())
//                .bloodPressure(healthData.getBloodPressure())
//                .smoking(healthData.getSmoking())
//                .drinking(healthData.getDrinking())
//                .diseases(healthData.getDisease().stream()
//                        .map(DiseaseResponseDto::from)
//                        .toList())
//                .medications(healthData.getMedication().stream()
//                        .map(MedicationResponseDto::from)
//                        .toList())
//                .allergies(healthData.getAllergy().stream()
//                        .map(AllergyResponseDto::from)
//                        .toList())
//                .createdAt(healthData.getCreatedAt())
//                .updatedAt(healthData.getUpdatedAt())
//                .build();
//
//        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
//    }

}
