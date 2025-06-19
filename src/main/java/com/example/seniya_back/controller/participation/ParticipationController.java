//package com.example.seniya_back.controller.participation;
//
//import com.example.seniya_back.dto.participation.response.ParticipationResponseDto;
//import com.example.seniya_back.dto.participation.response.ParticipationCancelResponseDto;
//import com.example.seniya_back.service.ParticipationService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/v1/participations")
//public class ParticipationController {
//
//    private final ParticipationService participationService;
//
//    /**
//     * 사용자가 신청한 수업 목록 조회
//     */
//    @GetMapping("/me")
//    public List<ParticipationResponseDto> getMyParticipations(@AuthenticationPrincipal(expression = "userId") Long userId) {
//        return participationService.getMyParticipations(userId);
//    }
//
//    /**
//     * 수업 신청 취소
//     */
//    @DeleteMapping("/{participationId}")
//    public ParticipationCancelResponseDto cancelParticipation(
//            @AuthenticationPrincipal(expression = "userId") Long userId,
//            @PathVariable Long participationId
//    ) {
//        return participationService.cancelParticipation(userId, participationId);
//    }
//}
