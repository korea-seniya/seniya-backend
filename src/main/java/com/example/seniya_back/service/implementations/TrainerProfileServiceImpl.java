package com.example.seniya_back.service.implementations;

import com.example.seniya_back.common.constants.ResponseCode;
import com.example.seniya_back.common.constants.ResponseMessage;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.trainer.requestDto.TrainerProfileRequestDto;
import com.example.seniya_back.dto.trainer.responseDto.TrainerProfileCreateResponseDto;
import com.example.seniya_back.dto.trainer.responseDto.TrainerProfileResponseDto;
import com.example.seniya_back.entity.Certificate;
import com.example.seniya_back.entity.TrainerProfile;
import com.example.seniya_back.entity.User;
import com.example.seniya_back.repository.CertificateRepository;
import com.example.seniya_back.repository.TrainerProfileRepository;
import com.example.seniya_back.repository.UserRepository;
import com.example.seniya_back.service.TrainerProfileService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.naming.NoPermissionException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainerProfileServiceImpl implements TrainerProfileService {
    private final UserRepository userRepository;
    private final TrainerProfileRepository trainerProfileRepository;
    private final CertificateRepository certificateRepository;

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

        TrainerProfile trainer = trainerProfileRepository.findByUser(user);

        if (!(trainer == null)) {
            throw new IllegalArgumentException(ResponseMessage.FAILED);
        }

        TrainerProfile newTrainerProfile = TrainerProfile.builder()
                .user(user)
                .specialty(dto.getSpecialty())
                .experienceYears(dto.getExperienceYears())
                .description(dto.getDescription())
                .build();

        List<Certificate> certificates = dto.getCertificates().stream()
                .map(certification -> Certificate.builder()
                        .certificate(certification.getCertificate())
                        .certificationDate(certification.getCertificationDate())
                        .build()
                )
                .collect(Collectors.toList());

        newTrainerProfile.setCertificates(certificates);

        trainerProfileRepository.save(newTrainerProfile);

        responseDto = TrainerProfileCreateResponseDto.builder()
                .name(newTrainerProfile.getUser().getName())
                .specialty(newTrainerProfile.getSpecialty())
                .certificates(newTrainerProfile.getCertificates())
                .experienceYears(newTrainerProfile.getExperienceYears())
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
                .certificates(trainer.getCertificates())
                .experienceYears(trainer.getExperienceYears())
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

        trainer.setExperienceYears(dto.getExperienceYears());
        trainer.setDescription(dto.getDescription());

        trainerProfileRepository.save(trainer);

        responseDto = TrainerProfileResponseDto.builder()
                .name(trainer.getUser().getName())
                .specialty(trainer.getSpecialty())
                .certificates(trainer.getCertificates())
                .experienceYears(trainer.getExperienceYears())
                .description(trainer.getDescription())
                .createdAt(trainer.getCreatedAt())
                .updatedAt(trainer.getUpdatedAt())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }
}
