package com.example.seniya_back.repository;

import com.example.seniya_back.entity.Participations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipationsRepository extends JpaRepository<Participations, Long> {
}
