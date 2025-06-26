package com.example.seniya_back.dto.trainer.responseDto;

import com.example.seniya_back.common.enums.Specialty;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PopularTrainerResponseDto {
    private Long trainerId;
    private String name;
    private Specialty specialty;
    private String profileImageUrl;
    private Integer courseCount;
}
