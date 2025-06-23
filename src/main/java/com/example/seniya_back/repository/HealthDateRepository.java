package com.example.seniya_back.repository;

import com.example.seniya_back.entity.HealthData;
import com.example.seniya_back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HealthDateRepository extends JpaRepository<HealthData, Long> {
    Optional<HealthData> findByUser(User user);
}
