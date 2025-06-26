package com.example.seniya_back.dto.pass.response;

import com.example.seniya_back.entity.Pass;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PassResponseDto {
    private Long passId;
    private String couponType;
    private boolean used;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;

    public static PassResponseDto fromEntity(Pass pass) {
        return PassResponseDto.builder()
                .passId(pass.getPassId())
                .couponType(pass.getCouponType().name())
                .used(pass.isUsed())
                .issuedAt(pass.getIssuedAt())
                .expiresAt(pass.getExpiresAt())
                .build();
    }
}
