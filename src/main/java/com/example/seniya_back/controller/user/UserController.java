package com.example.seniya_back.controller.user;

import com.example.seniya_back.common.constants.ApiMappingPattern;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.user.request.MyInfoUpdateRequestDto;
import com.example.seniya_back.dto.user.response.GetMyInfoResponseDto;
import com.example.seniya_back.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiMappingPattern.USER_API)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // === UserController mapping pattern === //
    private static final String GET_USER_INFO = "/me";
    private static final String PUT_USER_INFO = "/me";
    private static final String DELETE_USER = "/me";

    // 1) 회원 정보 조회
    @GetMapping(GET_USER_INFO)
    public ResponseEntity<ResponseDto<GetMyInfoResponseDto>> getUserInfo(
            @AuthenticationPrincipal String username
    ) {
        ResponseDto<GetMyInfoResponseDto> response = userService.getUserInfo(username);
        return ResponseEntity.ok(response);
    }

    // 2) 회원 정보 수정
    @PutMapping(PUT_USER_INFO)
    public ResponseEntity<ResponseDto<GetMyInfoResponseDto>> updateUserInfo(
            @AuthenticationPrincipal String username,
            @Valid @RequestBody MyInfoUpdateRequestDto dto
    ) {
        ResponseDto<GetMyInfoResponseDto> response = userService.updateUserInfo(username, dto);
        return ResponseEntity.ok(response);
    }

    // 3) 회원 탈퇴
   @DeleteMapping
    public ResponseEntity<ResponseDto<?>> deleteUser(
            @AuthenticationPrincipal String username
   ){
        ResponseDto<?> response = userService.deleteUser(username);
        return ResponseEntity.ok(response);
   }
}

