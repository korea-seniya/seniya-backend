package com.example.seniya_back.entity;

import com.example.seniya_back.common.enums.DiseaseStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "diseases")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Disease {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "disease_id", updatable = false)
    private Long diseaseId;

    @Column(name = "disease_name", nullable = false, length = 100)
    private String diseaseName;

    @Column(name = "disease_date")
    private LocalDate diseaseDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "disease_status")
    private DiseaseStatus diseaseStatus;
}
