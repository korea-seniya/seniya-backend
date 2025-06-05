package com.example.seniya_back.dto.admin.user.response;

import com.example.seniya_back.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class UserListRespDto {
    private List<User> users;
    private int availablePasses;
}
