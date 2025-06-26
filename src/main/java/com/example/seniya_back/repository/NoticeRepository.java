package com.example.seniya_back.repository;

import com.example.seniya_back.entity.Notice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {

    // 상단 고정된 공지 우선 정렬하여 최대 5개 반환 (사용 가능)
    List<Notice> findTop5ByOrderByPinnedDescCreatedAtDesc();

    // 상단 고정 + 최신순 정렬하여 페이징 지원
    @Query("SELECT n FROM Notice n ORDER BY n.pinned DESC, n.createdAt DESC")
    List<Notice> findNoticesWithPriority(Pageable pageable);

    // 예전 코드: 주석 처리 유지
//    List<Notice> findByTitleIgnoreCaseContaining(String title);
//    List<Notice> findByRoleIgnoreCaseContaining(String roleName);
}
