package com.example.seniya_back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "notices")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notice extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.ALL)
    private List<UploadFile> images = new ArrayList<>();
}
