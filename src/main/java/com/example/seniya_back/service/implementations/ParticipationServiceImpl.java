//package com.example.seniya_back.service.implementations;
//
//
//import com.example.seniya_back.dto.participation.response.ParticipationCancelResponseDto;
//import com.example.seniya_back.dto.participation.response.ParticipationInfoResponseDto;
//import com.example.seniya_back.dto.participation.response.ParticipationResponseDto;
//import com.example.seniya_back.entity.Participations;
//import com.example.seniya_back.repository.ParticipationsRepository;
//import com.example.seniya_back.service.ParticipationService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class ParticipationServiceImpl implements ParticipationService {
//
//    private final ParticipationsRepository participationsRepository;
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<ParticipationResponseDto> getMyParticipations(Long userId) {
//        List<Participations> participations = participationsRepository.findAllByUserId(userId);
//
//        return participations.stream()
//                .map(p -> new ParticipationResponseDto(
//                        p.getCourse().getTrainer().getName(),
//                        p.getCourse().getTitle(),
//                        p.getCourse().getDescription(),
//                        p.getCourse().getClassDate(),
//                        p.getCourse().getClassStartTime(),
//                        p.getCourse().getClassEndTime(),
//                        p.getCourse().getCategory(),
//                        p.getCourse().getClassroom()
//                ))
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public ParticipationCancelResponseDto cancelParticipation(Long userId, Long participationId) {
//        Participations participation = participationsRepository.findByIdAndUserId(participationId, userId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 수업 신청 정보를 찾을 수 없습니다."));
//
//        ParticipationInfoResponseDto info = new ParticipationInfoResponseDto(
//                participation.getCourse().getTrainer().getName(),
//                participation.getCourse().getTitle(),
//                participation.getCourse().getDescription(),
//                participation.getCourse().getClassDate(),
//                participation.getCourse().getClassStartTime(),
//                participation.getCourse().getClassEndTime(),
//                participation.getCourse().getCategory(),
//                participation.getCourse().getClassroom()
//        );
//
//        participationsRepository.delete(participation);
//
//        return new ParticipationCancelResponseDto("수업이 성공적으로 취소되었습니다.", info);
//    }
//}
