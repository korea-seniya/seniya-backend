package com.example.seniya_back.repository;

import com.example.seniya_back.entity.HealthData;
import com.example.seniya_back.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {
    void deleteAllByHealthData(HealthData healthData);
}
