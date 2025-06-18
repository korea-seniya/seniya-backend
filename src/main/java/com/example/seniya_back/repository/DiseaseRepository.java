package com.example.seniya_back.repository;

import com.example.seniya_back.entity.Disease;
import com.example.seniya_back.entity.HealthData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {
    void deleteAllByHealthData(HealthData healthData);

    List<Disease> findAllByHealthData(HealthData healthData);
}
