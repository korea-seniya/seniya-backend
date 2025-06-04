package com.example.seniya_back.dto.admin.user.response;

import com.example.seniya_back.dto.admin.course.response.CourseRespDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetUserDetailRespDto {
    private String name;
    private String phone;
    private String roleName;
    private BigDecimal amount;
    private int couponCount;
    private int availablePasses;
    private List<CourseRespDto> courses;
}
