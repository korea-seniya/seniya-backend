package com.example.seniya_back.dto.user.response;
import com.example.seniya_back.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignInResponseDto {
//    private String token; // jwt 토큰
     User user;
//    private int exprTime; // expire + time: (토큰) 만료 시간
}