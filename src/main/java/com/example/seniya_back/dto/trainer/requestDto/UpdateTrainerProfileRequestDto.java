package com.example.seniya_back.dto.trainer.requestDto;

import com.example.seniya_back.common.enums.Specialty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class UpdateTrainerProfileRequestDto {
    private Specialty specialty;
    private List<CertificateRequestDto> certificates;
    private Integer experienceYears;
    private String description;
    private boolean removeProfileImage;
}
