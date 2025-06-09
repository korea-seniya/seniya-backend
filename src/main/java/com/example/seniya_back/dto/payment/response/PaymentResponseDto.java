package com.example.seniya_back.dto.payment.response;

import com.example.seniya_back.common.enums.payment.Status;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDto {
    private Long paymentId;
    private Status status;
//    private String message;
}
