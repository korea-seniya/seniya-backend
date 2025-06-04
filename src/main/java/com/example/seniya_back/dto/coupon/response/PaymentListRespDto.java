package com.example.seniya_back.dto.coupon.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class PaymentListRespDto {
    private Long paymentId;
    private String name;
    private String phone;
    private int amount;
    private String method;
    private String status;
    private int couponCount;
    private LocalDateTime createdAt;
}
