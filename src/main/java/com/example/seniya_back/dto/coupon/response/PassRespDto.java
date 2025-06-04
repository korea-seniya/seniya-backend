package com.example.seniya_back.dto.coupon.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class PassRespDto {
    private Long passId;
    private String couponType;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;
    private boolean used;
    private LocalDateTime usedAt;
    private String message;
}
