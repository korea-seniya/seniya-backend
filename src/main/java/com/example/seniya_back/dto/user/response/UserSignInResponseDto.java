package com.example.seniya_back.dto.user.response;
import com.example.seniya_back.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignInResponseDto {
     private String token;
     private UserResponseDto user;
     private int exprTime;
     private int roleId;
}