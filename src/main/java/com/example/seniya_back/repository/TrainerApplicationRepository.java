package com.example.seniya_back.repository;

import com.example.seniya_back.entity.TrainerApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerApplicationRepository extends JpaRepository<TrainerApplication, Long> {
}
