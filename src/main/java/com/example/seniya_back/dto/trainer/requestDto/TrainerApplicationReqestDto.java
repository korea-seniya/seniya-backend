package com.example.seniya_back.dto.trainer.requestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TrainerApplicationReqestDto {
    @NotBlank
    private String specialty;
}
