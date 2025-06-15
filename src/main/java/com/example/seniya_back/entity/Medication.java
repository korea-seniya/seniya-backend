package com.example.seniya_back.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medications")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medication_id", updatable = false)
    private Long medicationId;

    @ManyToOne
    @JoinColumn(name = "disease_id")
    private Disease disease;

    @Column(name = "medication_name", nullable = false, length = 100)
    private String medicationName;

    @ManyToOne
    @JoinColumn(name = "healthdata_id")
    private HealthData healthData;
}