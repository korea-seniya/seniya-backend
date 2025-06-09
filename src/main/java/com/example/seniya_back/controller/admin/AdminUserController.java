package com.example.seniya_back.controller.admin;

import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.admin.user.response.GetAllUserResponseDto;
import com.example.seniya_back.dto.admin.user.response.GetUserDetailRespDto;
import com.example.seniya_back.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class AdminUserController {

    private final AdminUserService adminUserService;

    @GetMapping
    public ResponseEntity<ResponseDto<List<GetAllUserResponseDto>>> getAllUser() {
        ResponseDto<List<GetAllUserResponseDto>> response = adminUserService.getAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto<GetUserDetailRespDto>> getUserById(@PathVariable long id) {
        ResponseDto<GetUserDetailRespDto> response = adminUserService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
