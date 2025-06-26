package com.example.seniya_back.controller.participation;

import com.example.seniya_back.common.constants.ApiMappingPattern;
import com.example.seniya_back.dto.participation.response.ParticipationInfoResponseDto;
import com.example.seniya_back.dto.participation.response.ParticipationResponseDto;
import com.example.seniya_back.dto.participation.response.ParticipationCancelResponseDto;
import com.example.seniya_back.service.ParticipationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiMappingPattern.PARTICIPATION_API)
public class ParticipationController {

    private final ParticipationService participationService;

    // 사용자가 신청한 수업 목록 조회
    @GetMapping("/me")
    public List<ParticipationResponseDto> getMyParticipations(
            @AuthenticationPrincipal String username
    ) {
        System.out.println(">>> username: " + username);
        return participationService.getMyParticipations(username);
    }


    // 단일 수업 신청 정보 조회
    @GetMapping("/{participationId}")
    public ParticipationInfoResponseDto getParticipationInfo(
            @AuthenticationPrincipal(expression = "username") String username,
            @PathVariable Long participationId
    ) {
        return participationService.getParticipationInfo(username, participationId);
    }


    //수업 신청 취소
    @DeleteMapping("/{participationId}")
    public ParticipationCancelResponseDto cancelParticipation(
            @AuthenticationPrincipal String username,
            @PathVariable Long participationId
    ) {
        return participationService.cancelParticipation(username, participationId);
    }

}
