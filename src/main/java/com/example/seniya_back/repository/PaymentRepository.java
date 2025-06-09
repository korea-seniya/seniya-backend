package com.example.seniya_back.repository;

import com.example.seniya_back.entity.Payment;
import com.example.seniya_back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    int findByUser(User user);
}
