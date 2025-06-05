package com.example.seniya_back.dto.payment.request;

import com.example.seniya_back.common.enums.payment.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfirmPaymentRequestDto {
    private Status status;
    private LocalDateTime updatedAt;
}
