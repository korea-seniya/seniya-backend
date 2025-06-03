package com.example.seniya_back.dto.trainer.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetTrainerProfileRepsonseDto {
    private String name;
    private String specialty;
    private String certificate;
    private LocalDate certificateDate;
    private Integer experienceYears;
    private String description;
}
