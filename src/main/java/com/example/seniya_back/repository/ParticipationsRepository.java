package com.example.seniya_back.repository;

import com.example.seniya_back.dto.admin.course.response.CourseResponseDto;
import com.example.seniya_back.entity.Participations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipationsRepository extends JpaRepository<Participations, Long> {

//    List<Participations> findAllByUserId(Long userId);
}
