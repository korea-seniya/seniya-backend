package com.example.seniya_back.dto.coupon.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentReqDto {
    private String method;
    private int couponCount;
    private String couponType;
    private LocalDateTime createdAt;
}
