package com.example.seniya_back.dto.Inquiry.responseDto;

import com.example.seniya_back.entity.TrainerProfile;
import com.example.seniya_back.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InquiryByIdResDto {
    private String title;
    private User userName;
    private TrainerProfile trainerName;
    private String content;
    private String response;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
