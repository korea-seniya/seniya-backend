//package com.example.seniya_back.repository;
//
//import com.example.seniya_back.entity.Participations;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface ParticipationsRepository extends JpaRepository<Participations, Long> {
//    List<Participations> findAllByUserId(Long userId);
//    Optional<Participations> findByParticipationIdAndUser_UserId(Long participationId, Long userId);
//
//    Optional<Object> findByIdAndUserId(Long participationId, Long userId);
//}
