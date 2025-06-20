package com.example.seniya_back.dto.trainer.requestDto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificateRequestDto {
    private String certificate;
    private LocalDate certificationDate;
}
