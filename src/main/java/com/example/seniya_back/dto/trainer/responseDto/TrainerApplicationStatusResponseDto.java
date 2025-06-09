package com.example.seniya_back.dto.trainer.responseDto;

import com.example.seniya_back.common.enums.ApprovalStatus;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainerApplicationStatusResponseDto {
    private Long applicationId;
    private ApprovalStatus approvalStatus;
}
