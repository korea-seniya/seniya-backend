package com.example.seniya_back.controller.trainer;

import com.example.seniya_back.common.constants.ApiMappingPattern;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.trainer.requestDto.TrainerApplicationStatusRequestDto;
import com.example.seniya_back.dto.trainer.responseDto.TrainerApplicationDetailResponseDto;
import com.example.seniya_back.dto.trainer.responseDto.TrainerApplicationResponseDto;
import com.example.seniya_back.dto.trainer.responseDto.TrainerApplicationStatusResponseDto;
import com.example.seniya_back.service.TrainerApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiMappingPattern.TRAINER_APPLY_API)
@RequiredArgsConstructor
public class ApplicationController {
    private final TrainerApplicationService trainerApplicationService;

    // 권한 신청
    @PostMapping
    public ResponseEntity<ResponseDto<TrainerApplicationStatusResponseDto>> applyTrainer(
            @AuthenticationPrincipal String username
    ) {
        ResponseDto<TrainerApplicationStatusResponseDto> response = trainerApplicationService.applyTrainer(username);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 나의 트레이너 신청 조회
    @GetMapping("/me")
    public ResponseEntity<ResponseDto<TrainerApplicationStatusResponseDto>> getMyApplication(
            @AuthenticationPrincipal String username
    ) {
        ResponseDto<TrainerApplicationStatusResponseDto> response = trainerApplicationService.getMyApplication(username);
        return ResponseEntity.ok(response);
    }


    // 관리자용
    // 신청 전체 목록 조회
    @GetMapping
    public ResponseEntity<ResponseDto<List<TrainerApplicationResponseDto>>> getAllApplication() {
        ResponseDto<List<TrainerApplicationResponseDto>>response = trainerApplicationService.getAllApplication();
        return ResponseEntity.ok(response);
    }

    // 신청 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<TrainerApplicationDetailResponseDto>> getApplicationById(
            @PathVariable Long id
    ) {
        ResponseDto<TrainerApplicationDetailResponseDto> response = trainerApplicationService.getApplicationById(id);
        return ResponseEntity.ok(response);
    }

    // 신청 상태 변경
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto<TrainerApplicationStatusResponseDto>> updateStatus(
            @PathVariable Long id,
            @RequestBody TrainerApplicationStatusRequestDto dto
    ) {
        ResponseDto<TrainerApplicationStatusResponseDto> response = trainerApplicationService.updateStatus(id, dto);
        return ResponseEntity.ok(response);
    }
}
