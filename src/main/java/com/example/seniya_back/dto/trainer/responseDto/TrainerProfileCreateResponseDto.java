package com.example.seniya_back.dto.trainer.responseDto;

import com.example.seniya_back.common.enums.Specialty;
import com.example.seniya_back.entity.Certificate;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerProfileCreateResponseDto {
    private String name;
    private Specialty specialty;
    private List<Certificate> certificates;
    private Integer experienceYears;
    private String description;
    private LocalDateTime createdAt;
}