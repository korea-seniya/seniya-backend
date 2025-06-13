package com.example.seniya_back.repository;

import com.example.seniya_back.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
//    List<Notice> findByTitleIgnoreCaseContaining(String title);
//
//    List<Notice> findByRoleIgnoreCaseContaining(String roleName);
}
