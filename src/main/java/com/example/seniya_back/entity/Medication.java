package com.example.seniya_back.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "medications")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medication_id", updatable = false)
    private Long medicationId;

    @Column(name = "disease_id", nullable = false)
    private Long diseaseId;

    @Column(name = "medication_name", nullable = false, length = 100)
    private String medicationName;

    @ManyToOne
    @JoinColumn(name = "healthdata_id")
    private HealthData healthData;
}
