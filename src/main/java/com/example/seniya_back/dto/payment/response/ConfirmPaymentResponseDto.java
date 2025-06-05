package com.example.seniya_back.dto.payment.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ConfirmPaymentResponseDto {

    private Long passId;
    private Long userId;
    private String couponType;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;
    private boolean used;
    private String message;

}
