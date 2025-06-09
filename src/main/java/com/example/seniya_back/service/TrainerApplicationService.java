package com.example.seniya_back.service;

import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.trainer.requestDto.TrainerApplicationStatusRequestDto;
import com.example.seniya_back.dto.trainer.responseDto.TrainerApplicationDetailResponseDto;
import com.example.seniya_back.dto.trainer.responseDto.TrainerApplicationResponseDto;
import com.example.seniya_back.dto.trainer.responseDto.TrainerApplicationStatusResponseDto;

import java.util.List;

public interface TrainerApplicationService {
    ResponseDto<TrainerApplicationStatusResponseDto> applyTrainer(String username);

    ResponseDto<TrainerApplicationStatusResponseDto> getMyApplication(String username);

    ResponseDto<List<TrainerApplicationResponseDto>> getAllApplication();

    ResponseDto<TrainerApplicationDetailResponseDto> getApplicationById(Long id);

    ResponseDto<TrainerApplicationStatusResponseDto> updateStatus(Long id, TrainerApplicationStatusRequestDto dto);

}
