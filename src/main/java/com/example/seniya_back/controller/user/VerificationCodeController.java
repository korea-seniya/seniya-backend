package com.example.seniya_back.controller.user;

import com.example.seniya_back.dto.user.request.EmailVerifyRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/verification-codes")
public class VerificationCodeController {

    @PostMapping("/email")
    public ResponseEntity<?> sendEmailCode(@RequestBody EmailVerifyRequestDto request) {
        // Logic to send email
        return ResponseEntity.ok("Verification code sent");
    }
}
