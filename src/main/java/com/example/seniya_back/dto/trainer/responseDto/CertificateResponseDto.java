package com.example.seniya_back.dto.trainer.responseDto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificateResponseDto {
    private String certificate;
    private LocalDate certificationDate;
}
