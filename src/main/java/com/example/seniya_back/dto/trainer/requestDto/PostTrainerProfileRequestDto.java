package com.example.seniya_back.dto.trainer.requestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class PostTrainerProfileRequestDto {
    private String name;
    private String specialty;
    private String certificate;
    private LocalDate certificateDate;
    private Integer experienceYears;
    private String description;
}
