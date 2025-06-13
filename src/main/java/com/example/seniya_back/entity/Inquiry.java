package com.example.seniya_back.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inquiries")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Inquiry extends BaseTimeEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private TrainerProfile trainer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private String response;

    @Column(nullable = false)
    private Boolean isPrivated;

    private LocalDateTime responsedAt = LocalDateTime.now();

}
