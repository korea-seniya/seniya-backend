package com.example.seniya_back.repository;

import com.example.seniya_back.common.enums.uploadFile.TargetType;
import com.example.seniya_back.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
    List<UploadFile> findByTargetIdAndTargetType(Long targetId, TargetType type);

    Optional<UploadFile> findFirstByTargetIdAndTargetType(Long targetId, TargetType targetType);
}
