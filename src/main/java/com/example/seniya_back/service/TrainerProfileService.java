package com.example.seniya_back.service;

import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.trainer.requestDto.TrainerProfileRequestDto;
import com.example.seniya_back.dto.trainer.requestDto.UpdateTrainerProfileRequestDto;
import com.example.seniya_back.dto.trainer.responseDto.PopularTrainerResponseDto;
import com.example.seniya_back.dto.trainer.responseDto.TrainerProfileCreateResponseDto;
import com.example.seniya_back.dto.trainer.responseDto.TrainerProfileResponseDto;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.NoPermissionException;
import java.io.IOException;

public interface TrainerProfileService {
    ResponseDto<TrainerProfileCreateResponseDto> createProfile(String username, @Valid TrainerProfileRequestDto dto, MultipartFile file) throws NoPermissionException, IOException;

    ResponseDto<TrainerProfileResponseDto> getTrainerProfile(String username) throws NoPermissionException;

    ResponseDto<TrainerProfileResponseDto> updateProfile(String username, @Valid UpdateTrainerProfileRequestDto dto, MultipartFile file) throws NoPermissionException, IOException;

    ResponseDto<PopularTrainerResponseDto> popularTrainer();
}
