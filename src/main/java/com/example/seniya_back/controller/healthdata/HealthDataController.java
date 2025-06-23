package com.example.seniya_back.controller.healthdata;

import com.example.seniya_back.common.constants.ApiMappingPattern;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.healthdata.request.HealthDataRequestDto;
import com.example.seniya_back.dto.healthdata.request.HealthDataUpdRequestDto;
import com.example.seniya_back.dto.healthdata.response.HealthDataResponseDto;
import com.example.seniya_back.service.HealthDataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMappingPattern.HEALTH_DATA_API)
@RequiredArgsConstructor
public class HealthDataController {
    private final HealthDataService healthDataService;

    // 건강 데이터 생성
    @PostMapping
    public ResponseEntity<ResponseDto<HealthDataResponseDto>> createHealthData(@AuthenticationPrincipal String username, @Valid @RequestBody HealthDataRequestDto dto) {
        ResponseDto<HealthDataResponseDto> healthdata = healthDataService.createHealthData(username, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(healthdata);
    }

    // 건강 데이터 수정
    @PutMapping("/update")
    public ResponseEntity<ResponseDto<HealthDataResponseDto>> updateHealthData(@AuthenticationPrincipal String username,  @Valid @RequestBody HealthDataUpdRequestDto dto) {
        ResponseDto<HealthDataResponseDto> response = healthDataService.updateHealthData(username, dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseDto<HealthDataResponseDto>> getMyHealthData(
            @AuthenticationPrincipal String username
    ) {
        ResponseDto<HealthDataResponseDto> healthdata = healthDataService.getHealthDataByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(healthdata);
    }

}
