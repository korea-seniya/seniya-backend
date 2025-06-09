package com.example.seniya_back.service.implementations;

import com.example.seniya_back.common.constants.ResponseCode;
import com.example.seniya_back.common.constants.ResponseMessage;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.trainer.requestDto.TrainerApplicationStatusRequestDto;
import com.example.seniya_back.dto.trainer.responseDto.TrainerApplicationDetailResponseDto;
import com.example.seniya_back.dto.trainer.responseDto.TrainerApplicationResponseDto;
import com.example.seniya_back.dto.trainer.responseDto.TrainerApplicationStatusResponseDto;
import com.example.seniya_back.entity.TrainerApplication;
import com.example.seniya_back.entity.User;
import com.example.seniya_back.repository.TrainerApplicationRepository;
import com.example.seniya_back.repository.UserRepository;
import com.example.seniya_back.service.TrainerApplicationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerApplicationServiceImpl implements TrainerApplicationService {
    private final TrainerApplicationRepository trainerApplicationRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseDto<TrainerApplicationStatusResponseDto> applyTrainer(String username) {
        TrainerApplicationStatusResponseDto responseDto = null;

        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));

        TrainerApplication newTrainer = TrainerApplication.builder()
                .user(user)
                .build();

        trainerApplicationRepository.save(newTrainer);

        responseDto = TrainerApplicationStatusResponseDto.builder()
                .approvalStatus(newTrainer.getApprovalStatus())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }

    @Override
    public ResponseDto<TrainerApplicationStatusResponseDto> getMyApplication(String username) {
        TrainerApplicationStatusResponseDto responseDto = null;

        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));

        TrainerApplication trainerApplication = trainerApplicationRepository.getTrainerApplicationByUser(user);

        responseDto = TrainerApplicationStatusResponseDto.builder()
                .approvalStatus(trainerApplication.getApprovalStatus())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }

    @Override
    public ResponseDto<List<TrainerApplicationResponseDto>> getAllApplication() {
        List<TrainerApplicationResponseDto> responseDtos = null;

        List<TrainerApplication> trainerApplications = trainerApplicationRepository.findAll();

        responseDtos = trainerApplications.stream().map(
                trainerApplication -> TrainerApplicationResponseDto.builder()
                        .username(trainerApplication.getUser().getUserName())
                        .appliedDate(trainerApplication.getAppliedDate())
                        .approvalStatus(trainerApplication.getApprovalStatus())
                        .createdAt(trainerApplication.getCreatedAt())
                        .updatedAt(trainerApplication.getUpdatedAt())
                        .build()
        ).collect(Collectors.toList());

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDtos).getBody();
    }

    @Override
    public ResponseDto<TrainerApplicationDetailResponseDto> getApplicationById(Long id) {
        TrainerApplicationDetailResponseDto responseDto = null;

        TrainerApplication trainerApplication = trainerApplicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.RESOURCE_NOT_FOUND));

        responseDto = TrainerApplicationDetailResponseDto.builder()
                .username(trainerApplication.getUser().getUserName())
                .name(trainerApplication.getUser().getName())
                .userEmail(trainerApplication.getUser().getEmail())
                .appliedDate(trainerApplication.getAppliedDate())
                .approvalStatus(trainerApplication.getApprovalStatus())
                .createdAt(trainerApplication.getCreatedAt())
                .updatedAt(trainerApplication.getUpdatedAt())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }

    @Override
    public ResponseDto<TrainerApplicationStatusResponseDto> updateStatus(Long id, TrainerApplicationStatusRequestDto dto) {
        TrainerApplicationStatusResponseDto responseDto = null;

        TrainerApplication trainerApplication = trainerApplicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.RESOURCE_NOT_FOUND));

        trainerApplication.setApprovalStatus(dto.getApprovalStatus());

        responseDto = TrainerApplicationStatusResponseDto.builder()
                .applicationId(trainerApplication.getApplicationId())
                .approvalStatus(trainerApplication.getApprovalStatus())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }
}
