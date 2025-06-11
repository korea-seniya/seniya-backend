package com.example.seniya_back.repository;

import com.example.seniya_back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    boolean existByPhone(String phone);
//    boolean existsByEmail(String email);

    Optional<User> findByUserName(String username);
}
