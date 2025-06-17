package com.example.seniya_back.repository;

import com.example.seniya_back.entity.Allergy;
import com.example.seniya_back.entity.HealthData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergyRepository extends JpaRepository<Allergy, Long> {
    void deleteAllByHealthData(HealthData healthData);
}
