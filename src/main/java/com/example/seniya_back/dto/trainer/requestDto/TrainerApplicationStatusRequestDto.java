package com.example.seniya_back.dto.trainer.requestDto;

import com.example.seniya_back.common.enums.ApprovalStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TrainerApplicationStatusRequestDto {
    private ApprovalStatus approvalStatus;
}
