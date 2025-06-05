package com.example.seniya_back.dto.payment.response;

import com.example.seniya_back.common.enums.payment.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class GetAllPaymentResponseDto {
    private Long paymentId;
    private String name;
    private String phone;
    private int amount;
    private String method;
    private Status status;
    private int couponCount;
    private LocalDateTime createdAt;
}
