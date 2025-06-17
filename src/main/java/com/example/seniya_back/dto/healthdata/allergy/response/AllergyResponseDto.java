package com.example.seniya_back.dto.healthdata.allergy.response;

import com.example.seniya_back.entity.Allergy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AllergyResponseDto {
    private Long allergyId;
    private String allergyName;
    private String reaction;

    public static AllergyResponseDto from(Allergy allergy) {
        return AllergyResponseDto.builder()
                .allergyId(allergy.getAllergyId())
                .allergyName(allergy.getAllergyName())
                .reaction(allergy.getReaction())
                .build();
    }

}
