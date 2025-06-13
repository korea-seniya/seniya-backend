package com.example.seniya_back.controller.trainer;

import com.example.seniya_back.common.constants.ApiMappingPattern;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.trainer.requestDto.TrainerProfileRequestDto;
import com.example.seniya_back.dto.trainer.responseDto.TrainerProfileCreateResponseDto;
import com.example.seniya_back.dto.trainer.responseDto.TrainerProfileResponseDto;
import com.example.seniya_back.service.TrainerProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.naming.NoPermissionException;

@RestController
@RequestMapping(ApiMappingPattern.TRAINER_PROFILE_API)
@RequiredArgsConstructor
public class ProfileController {
    private final TrainerProfileService trainerProfileService;

    @PostMapping("/me")
    public ResponseEntity<ResponseDto<TrainerProfileCreateResponseDto>> createProfile(
            @AuthenticationPrincipal String username,
            @Valid @RequestBody TrainerProfileRequestDto dto
    ) throws NoPermissionException {
        ResponseDto<TrainerProfileCreateResponseDto> response = trainerProfileService.createProfile(username, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseDto<TrainerProfileResponseDto>> getTrainerProfile(
            @AuthenticationPrincipal String username
    ) throws NoPermissionException {
        ResponseDto<TrainerProfileResponseDto> response = trainerProfileService.getTrainerProfile(username);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/me")
    public ResponseEntity<ResponseDto<TrainerProfileResponseDto>> updateProfile(
            @AuthenticationPrincipal String username,
            @Valid @RequestBody TrainerProfileRequestDto dto
    ) throws NoPermissionException {
        ResponseDto<TrainerProfileResponseDto> response = trainerProfileService.updateProfile(username, dto);
        return ResponseEntity.ok(response);
    }
}
