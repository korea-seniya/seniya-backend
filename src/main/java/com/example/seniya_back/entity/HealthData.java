package com.example.seniya_back.entity;

import com.example.seniya_back.common.enums.BloodPressure;
import jakarta.persistence.*;
        import lombok.*;

        import java.time.LocalDateTime;
import java.util.List;

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

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
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

    @OneToMany(mappedBy = "healthData", cascade = CascadeType.ALL)
    private List<Disease> disease;

    @OneToMany(mappedBy = "healthData", cascade = CascadeType.ALL)
    private List<Medication> medication;

    @OneToMany(mappedBy = "healthData", cascade = CascadeType.ALL)
    private List<Allergy> allergy;

    @Column(name = "smoking", nullable = false)
    private Boolean smoking = false;

    @Column(name = "drinking", nullable = false)
    private Boolean drinking  = false;



}
