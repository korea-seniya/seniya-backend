package com.example.seniya_back.entity;

import com.example.seniya_back.common.enums.DiseaseStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "diseases")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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

    @ManyToOne
    @JoinColumn(name = "health_data_id")
    private HealthData healthData;

    @OneToMany(mappedBy = "disease", cascade = CascadeType.ALL)
    private List<Medication> medication;

    @Enumerated(EnumType.STRING)
    @Column(name = "disease_status")
    private DiseaseStatus diseaseStatus;
}
