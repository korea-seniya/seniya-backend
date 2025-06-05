package com.example.seniya_back.dto.payment.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePaymentRequestDto {
    private String method;
    private int couponCount;
    private String couponType;
    private LocalDateTime createdAt;
}
