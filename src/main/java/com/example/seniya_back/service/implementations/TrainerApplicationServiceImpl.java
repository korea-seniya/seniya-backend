package com.example.seniya_back.service.implementations;

import com.example.seniya_back.common.constants.ResponseCode;
import com.example.seniya_back.common.constants.ResponseMessage;
import com.example.seniya_back.common.enums.ApprovalStatus;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.trainer.requestDto.TrainerApplicationStatusRequestDto;
import com.example.seniya_back.dto.trainer.responseDto.TrainerApplicationDetailResponseDto;
import com.example.seniya_back.dto.trainer.responseDto.TrainerApplicationResponseDto;
import com.example.seniya_back.dto.trainer.responseDto.TrainerApplicationStatusResponseDto;
import com.example.seniya_back.entity.Role;
import com.example.seniya_back.entity.TrainerApplication;
import com.example.seniya_back.entity.User;
import com.example.seniya_back.repository.RoleRepository;
import com.example.seniya_back.repository.TrainerApplicationRepository;
import com.example.seniya_back.repository.UserRepository;
import com.example.seniya_back.service.TrainerApplicationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerApplicationServiceImpl implements TrainerApplicationService {
    private final TrainerApplicationRepository trainerApplicationRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public ResponseDto<TrainerApplicationStatusResponseDto> applyTrainer(String username) {
        TrainerApplicationStatusResponseDto responseDto = null;

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));

        TrainerApplication trainerApplication = trainerApplicationRepository.getTrainerApplicationByUser(user);

        if (!(trainerApplication == null)) {
            throw new IllegalArgumentException(ResponseMessage.USER_ALREADY_EXISTS);
        }

        TrainerApplication newTrainer = TrainerApplication.builder()
                .user(user)
                .approvalStatus(ApprovalStatus.PENDING)
                .build();

        trainerApplicationRepository.save(newTrainer);

        responseDto = TrainerApplicationStatusResponseDto.builder()
                .applicationId(newTrainer.getApplicationId())
                .approvalStatus(newTrainer.getApprovalStatus())
                .createdAt(newTrainer.getCreatedAt())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }

    @Override
    public ResponseDto<TrainerApplicationStatusResponseDto> getMyApplication(String username) {
        TrainerApplicationStatusResponseDto responseDto = null;

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));

        TrainerApplication trainerApplication = trainerApplicationRepository.getTrainerApplicationByUser(user);

        responseDto = TrainerApplicationStatusResponseDto.builder()
                .applicationId(trainerApplication.getApplicationId())
                .approvalStatus(trainerApplication.getApprovalStatus())
                .appliedDate(trainerApplication.getAppliedDate())
                .createdAt(trainerApplication.getCreatedAt())
                .updatedAt(trainerApplication.getUpdatedAt())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }

    @Override
    public ResponseDto<List<TrainerApplicationResponseDto>> getAllApplication() {
        List<TrainerApplicationResponseDto> responseDtos = null;

        List<TrainerApplication> trainerApplications = trainerApplicationRepository.findAll();

        responseDtos = trainerApplications.stream().map(
                trainerApplication -> TrainerApplicationResponseDto.builder()
                        .id(trainerApplication.getApplicationId())
                        .username(trainerApplication.getUser().getUsername())
                        .name(trainerApplication.getUser().getName())
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
                .username(trainerApplication.getUser().getUsername())
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

        Role trainerRole = roleRepository.findByRoleName("TRAINER")
                .orElseGet(() -> roleRepository.save(Role.builder().roleName("TRAINER").build()));

        String status = dto.getApprovalStatus().toString();
        trainerApplication.setApprovalStatus(dto.getApprovalStatus());

        if (status.equals("APPROVE")){
             trainerApplication.setAppliedDate(LocalDate.now());
             User user = trainerApplication.getUser();
             user.setRole(trainerRole);
             userRepository.save(user);
        }

        trainerApplicationRepository.save(trainerApplication);



        responseDto = TrainerApplicationStatusResponseDto.builder()
                .applicationId(trainerApplication.getApplicationId())
                .approvalStatus(trainerApplication.getApprovalStatus())
                .appliedDate(trainerApplication.getAppliedDate())
                .updatedAt(trainerApplication.getUpdatedAt())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }
}
