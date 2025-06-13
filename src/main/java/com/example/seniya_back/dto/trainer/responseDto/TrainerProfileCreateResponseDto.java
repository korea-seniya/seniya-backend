package com.example.seniya_back.dto.trainer.responseDto;

import com.example.seniya_back.common.enums.Specialty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerProfileCreateResponseDto {
    private String name;
    private Specialty specialty;
    private String certificate;
    private LocalDate certificateDate;
    private Integer experienceYears;
    private String description;
    private LocalDateTime createdAt;
}