package com.example.seniya_back.service;

import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.trainer.requestDto.TrainerProfileRequestDto;
import com.example.seniya_back.dto.trainer.responseDto.TrainerProfileCreateResponseDto;
import com.example.seniya_back.dto.trainer.responseDto.TrainerProfileResponseDto;
import jakarta.validation.Valid;

import javax.naming.NoPermissionException;

public interface TrainerProfileService {
    ResponseDto<TrainerProfileCreateResponseDto> createProfile(String username, @Valid TrainerProfileRequestDto dto) throws NoPermissionException;

    ResponseDto<TrainerProfileResponseDto> getTrainerProfile(String username) throws NoPermissionException;

    ResponseDto<TrainerProfileResponseDto> updateProfile(String username, @Valid TrainerProfileRequestDto dto) throws NoPermissionException;
}
