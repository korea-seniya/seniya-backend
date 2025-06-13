package com.example.seniya_back.dto.trainer.requestDto;

import com.example.seniya_back.common.enums.Specialty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class TrainerProfileRequestDto {
    private Specialty specialty;
    private String certificate;
    private LocalDate certificationDate;
    private Integer experienceYears;
    private String description;
}
