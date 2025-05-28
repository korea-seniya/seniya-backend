package com.example.seniya_back.entity;

import com.example.seniya_back.common.enums.Category;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "classes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "categry", nullable = false)
    private Category category;

    @Column(nullable = false)
    private Long trainerId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(name = "class_date", nullable = false)
    private LocalDateTime date;

    @Column(name = "class_start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "class_end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "class_room", nullable = false)
    private String room;
}
