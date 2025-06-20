package com.example.seniya_back.dto.trainer.responseDto;

import com.example.seniya_back.common.enums.ApprovalStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerApplicationStatusResponseDto {
    private Long applicationId;
    private ApprovalStatus approvalStatus;
    private LocalDate appliedDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
