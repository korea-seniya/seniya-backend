package com.example.seniya_back.dto.coupon.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRespDto {
    private Long paymentId;
    private String status;
    private String message;
}
