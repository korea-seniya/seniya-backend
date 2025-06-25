package com.example.seniya_back.dto.admin.user.response;

import com.example.seniya_back.common.enums.Category;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserDetailFlatRow {
    private String userName;
    private String phone;
    private String roleName;
    private BigDecimal totalAmount;
    private int totalCouponCount;
    private int availableCouponCount;

    private Long courseId;
    private String title;
    private String description;
    private LocalDateTime courseDate;
    private LocalTime courseStartTime;
    private LocalTime courseEndTime;
    private Category category;
    private String courseRoom;
    private LocalDateTime courseCreatedAt;
    private LocalDateTime courseUpdatedAt;
    private Long trainerId;
    private String trainerName;


}
