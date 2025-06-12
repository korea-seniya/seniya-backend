package com.example.seniya_back.entity;

import com.example.seniya_back.common.enums.uploadFile.TargetType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "upload_files")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uploadFileId;

    @Column(nullable = false)
    private String originalName;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String filePath;

    private String fileType;

    @Column(nullable = false)
    private long fileSize;

    @Column(nullable = false)
    private Long targetId;

    @Enumerated(EnumType.STRING)
    @Column
    private TargetType targetType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

}
