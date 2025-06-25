package com.example.seniya_back.repository;

import com.example.seniya_back.entity.Course;
import com.example.seniya_back.entity.Participations;
import com.example.seniya_back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParticipationsRepository extends JpaRepository<Participations, Long> {
    List<Participations> findAllByUser_Username(String username);

    Optional<Participations> findByParticipationIdAndUser_Username(Long participationId, String username);

    boolean existsByUserAndCourse(User user, Course course);

}
