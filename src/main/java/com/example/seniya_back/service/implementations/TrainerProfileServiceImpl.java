package com.example.seniya_back.service.implementations;

import com.example.seniya_back.common.constants.ResponseCode;
import com.example.seniya_back.common.constants.ResponseMessage;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.trainer.requestDto.TrainerProfileRequestDto;
import com.example.seniya_back.dto.trainer.responseDto.TrainerProfileCreateResponseDto;
import com.example.seniya_back.dto.trainer.responseDto.TrainerProfileResponseDto;
import com.example.seniya_back.entity.TrainerProfile;
import com.example.seniya_back.entity.User;
import com.example.seniya_back.repository.TrainerProfileRepository;
import com.example.seniya_back.repository.UserRepository;
import com.example.seniya_back.service.TrainerProfileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.naming.NoPermissionException;

@Service
@RequiredArgsConstructor
public class TrainerProfileServiceImpl implements TrainerProfileService {
    private final UserRepository userRepository;
    private final TrainerProfileRepository trainerProfileRepository;

    @Override
    public ResponseDto<TrainerProfileCreateResponseDto> createProfile(String username, TrainerProfileRequestDto dto)
            throws NoPermissionException {
        TrainerProfileCreateResponseDto responseDto = null;

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));

        String roleName = user.getRole().getRoleName();

        if(!roleName.equals("TRAINER")) {
            throw new NoPermissionException(ResponseMessage.NO_PERMISSION);
        }

        TrainerProfile newTrainerProfile = TrainerProfile.builder()
                .user(user)
                .specialty(dto.getSpecialty())
                .certificate(dto.getCertificate())
                .certificateDate(dto.getCertificateDate())
                .experienceYear(dto.getExperienceYears())
                .description(dto.getDescription())
                .build();

        trainerProfileRepository.save(newTrainerProfile);

        responseDto = TrainerProfileCreateResponseDto.builder()
                .name(newTrainerProfile.getUser().getName())
                .specialty(newTrainerProfile.getSpecialty())
                .certificate(newTrainerProfile.getCertificate())
                .certificateDate(newTrainerProfile.getCertificateDate())
                .experienceYears(newTrainerProfile.getExperienceYear())
                .description(newTrainerProfile.getDescription())
                .createdAt(newTrainerProfile.getCreatedAt())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }

    @Override
    public ResponseDto<TrainerProfileResponseDto> getTrainerProfile(String username) throws NoPermissionException {
        TrainerProfileResponseDto responseDto = null;

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));

        String roleName = user.getRole().getRoleName();

        if(!roleName.equals("TRAINER")) {
            throw new NoPermissionException(ResponseMessage.NO_PERMISSION);
        }

        TrainerProfile trainer = trainerProfileRepository.findByUser(user);

        responseDto = TrainerProfileResponseDto.builder()
                .name(trainer.getUser().getName())
                .specialty(trainer.getSpecialty())
                .certificate(trainer.getCertificate())
                .certificateDate(trainer.getCertificateDate())
                .experienceYears(trainer.getExperienceYear())
                .description(trainer.getDescription())
                .createdAt(trainer.getCreatedAt())
                .updatedAt(trainer.getUpdatedAt())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }

    @Override
    public ResponseDto<TrainerProfileResponseDto> updateProfile(String username, TrainerProfileRequestDto dto) throws NoPermissionException {
        TrainerProfileResponseDto responseDto = null;

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));

        String roleName = user.getRole().getRoleName();

        if(!roleName.equals("TRAINER")) {
            throw new NoPermissionException(ResponseMessage.NO_PERMISSION);
        }

        TrainerProfile trainer = trainerProfileRepository.findByUser(user);

        trainer.setSpecialty(dto.getSpecialty());
        trainer.setCertificate(dto.getCertificate());
        trainer.setCertificateDate(dto.getCertificateDate());
        trainer.setExperienceYear(dto.getExperienceYears());
        trainer.setDescription(dto.getDescription());

        trainerProfileRepository.save(trainer);

        responseDto = TrainerProfileResponseDto.builder()
                .name(trainer.getUser().getName())
                .specialty(trainer.getSpecialty())
                .certificate(trainer.getCertificate())
                .certificateDate(trainer.getCertificateDate())
                .experienceYears(trainer.getExperienceYear())
                .description(trainer.getDescription())
                .createdAt(trainer.getCreatedAt())
                .updatedAt(trainer.getUpdatedAt())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }
}
