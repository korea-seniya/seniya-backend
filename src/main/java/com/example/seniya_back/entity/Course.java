package com.example.seniya_back.entity;

import com.example.seniya_back.common.enums.Category;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "courses")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long courseId;

    @Enumerated(EnumType.STRING)
    @Column(name = "categry", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "tariner_id")
    private TrainerProfile trainerProfile;

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

    @Column(name = "class_room", nullable = false)
    private String room;
}
