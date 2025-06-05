package com.example.seniya_back.dto.trainer.responseDto;

import com.example.seniya_back.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AllTrainerApplicationResponseDto {
    private User user;
    private String name;
    private LocalDate appliedDate;
    private String approvalStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
