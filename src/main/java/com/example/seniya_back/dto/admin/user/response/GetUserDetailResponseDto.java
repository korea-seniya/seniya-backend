package com.example.seniya_back.dto.admin.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetUserDetailResponseDto {
    private String userName;
    private String phone;
    private String roleName;

    private BigDecimal totalAmount;
    private int totalCouponCount;
    private int availableCouponCount;
    private List<GetUserCourseResponseDto> courses;
}
