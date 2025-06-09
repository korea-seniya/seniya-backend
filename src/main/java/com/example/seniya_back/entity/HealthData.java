package com.example.seniya_back.entity;

import com.example.seniya_back.common.enums.BloodPressure;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "health_data")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Builder
public class HealthData extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "health_data_id", updatable = false)
    private Long healthDataId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "height", nullable = false)
    private Float height;

    @Column(name = "weight", nullable = false)
    private Float weight;

    @Column(name = "body_fat_percentage")
    private Float bodyFatPercentage;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_pressure")
    private BloodPressure bloodPressure;

    @ManyToOne
    @JoinColumn(name = "disease_id", nullable = false)
    private Disease diseaseId;

    @ManyToOne
    @JoinColumn(name = "medication_id", nullable = false)
    private Medication medicationId;

    @ManyToOne
    @JoinColumn(name = "allergy_id", nullable = false)
    private Allergy allergyId;

    @Column(name = "smoking", nullable = false)
    private Boolean smoking = false;

    @Column(name = "drinking", nullable = false)
    private Boolean drinking  = false;



}
