package com.example.seniya_back.service;

import com.example.seniya_back.dto.participation.response.ParticipationCancelResponseDto;
import com.example.seniya_back.dto.participation.response.ParticipationInfoResponseDto;
import com.example.seniya_back.dto.participation.response.ParticipationResponseDto;

import java.util.List;

public interface ParticipationService {
    List<ParticipationResponseDto> getMyParticipations(String username);
    ParticipationCancelResponseDto cancelParticipation(String username, Long participationId);

    ParticipationInfoResponseDto getParticipationInfo(String username, Long participationId);
}
