package com.example.seniya_back.controller.user;

import com.example.seniya_back.common.constants.ApiMappingPattern;
import com.example.seniya_back.dto.ResponseDto;
;
import com.example.seniya_back.dto.user.request.EmailSendRequestDto;
import com.example.seniya_back.dto.user.request.UserPasswordResetRequestDto;
import com.example.seniya_back.dto.user.request.UserSignInRequestDto;
import com.example.seniya_back.dto.user.request.UserSignUpRequestDto;
import com.example.seniya_back.dto.user.response.UserSignInResponseDto;
import com.example.seniya_back.dto.user.response.UserSignUpResponseDto;
import com.example.seniya_back.service.AuthService;
 import com.example.seniya_back.service.MailService;
import com.example.seniya_back.service.MailService;
import com.example.seniya_back.service.MailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping(ApiMappingPattern.AUTH_API)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final MailService mailService;

    private static final String POST_SIGN_UP = "/signup";
    private static final String POST_SIGN_IN = "/login";
    private static final String POST_LOG_OUT = "/logout";
    private static final String EMAIL_API = "/email";

    // 1) 회원가입

    @PostMapping(POST_SIGN_UP)
    public ResponseEntity<ResponseDto<UserSignUpResponseDto>> signup(@Valid @RequestBody UserSignUpRequestDto dto) {
        ResponseDto<UserSignUpResponseDto> response = authService.signup(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2) 로그인
    @PostMapping(POST_SIGN_IN)
    public ResponseEntity<ResponseDto<UserSignInResponseDto>> login(@Valid @RequestBody UserSignInRequestDto dto) {
        ResponseDto<UserSignInResponseDto> response = authService.login(dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

   // 3) 로그아웃
    @PostMapping(POST_LOG_OUT)
    public ResponseEntity<ResponseDto<?>> logout(@AuthenticationPrincipal String username) {
        ResponseDto<?> response = authService.logout(username);
       return ResponseEntity.status(HttpStatus.OK).body(response);
    } // 안됨 이거ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ이거 기능이 없음.

    // 4) 이메일 인증
    @PostMapping(EMAIL_API)
    public Mono<ResponseEntity<String>> sendEmail(@Valid @RequestBody EmailSendRequestDto dto) {
        return mailService.sendSimpleMessage(dto.getEmail());
    }


    // 5) 비밀번호 재설정
    @PutMapping("/reset-password")
    public Mono<ResponseEntity<String>> resetPassword(@Valid @RequestBody UserPasswordResetRequestDto dto) {
        return authService.resetPassword(dto);
    }
}
