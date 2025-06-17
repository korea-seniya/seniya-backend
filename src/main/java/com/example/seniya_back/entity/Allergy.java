package com.example.seniya_back.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "allergies")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Allergy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "allergy_id", updatable = false)
    private Long allergyId;

    @Column(name = "allergy_name", nullable = false, length = 100)
    private String allergyName;

    @Column(name = "reaction", nullable = false, length = 100)
    private String reaction;

    @ManyToOne
    @JoinColumn(name = "health_data_id")
    private HealthData healthData;
}