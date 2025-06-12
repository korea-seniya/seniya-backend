package com.example.seniya_back.repository;

import com.example.seniya_back.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    Optional<User> findByUserName(String username);

    Optional<Object> findByEmail(@Email(message = "올바른 이메일 형식이어야 합니다.") @NotBlank(message = "이메일은 필수 입력 값 입니다.") String email);

}
