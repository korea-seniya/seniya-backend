package com.example.seniya_back.dto.coupon.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentReqDto {
    private Long userId;
    private String method;
    private int couponCount;
    private String couponType;
}
