package com.example.seniya_back.repository;

import com.example.seniya_back.entity.Pass;
import com.example.seniya_back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassRepository extends JpaRepository<Pass, Long> {
    int findByUser(User user);
}
