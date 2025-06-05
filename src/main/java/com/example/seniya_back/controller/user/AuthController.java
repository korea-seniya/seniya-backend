package com.example.seniya_back.controller.user;

import com.example.seniya_back.common.constants.ApiMappingPattern;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.user.request.EmailVerificationCodeRequestDto;
import com.example.seniya_back.dto.user.request.UserPasswordResetRequestDto;
import com.example.seniya_back.dto.user.request.UserSignInRequestDto;
import com.example.seniya_back.dto.user.request.UserSignUpRequestDto;
import com.example.seniya_back.dto.user.response.UserSignInResponseDto;
import com.example.seniya_back.dto.user.response.UserSignUpResponseDto;
import com.example.seniya_back.service.AuthService;
import com.example.seniya_back.service.MailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(ApiMappingPattern.AUTH_API)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final MailService mailService;

    // === AuthController mapping pattern === //
    private static final String POST_SIGN_UP = "/signup";
    private static final String POST_SIGN_IN = "/login";

    // 1) 회원가입
    // - HTTP 메서드: POST
    // - URI 경로: /signup
    // @Params: UserSignUpRequestDto
    // @Return: UserSignUpResponseDto
    @PostMapping(POST_SIGN_UP)
    public ResponseEntity<ResponseDto<UserSignUpResponseDto>> signup(@Valid @RequestBody UserSignUpRequestDto dto) {
        System.out.println("=== 회원가입 요청 도착 ===");
        ResponseDto<UserSignUpResponseDto> response = authService.signup(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2) 로그인
    // - HTTP 메서드: POST
    // - URI 경로: /login
    // @Params: UserSignInRequestDto
    // @Return: UserSignInResponseDto

    // GET VS "POST"
    // : POST 사용을 권장
    // - 로그인 과정에서 사용자 이름과 비밀번호와 같은 민감한 데이터를 서버로 전송하기 때문
    // cf) GET 요청은 URL에 데이터가 노출(데이터 조회 시 데이터 구분값(PK) 요청 시 사용)
    @PostMapping(POST_SIGN_IN)
    public ResponseEntity<ResponseDto<UserSignInResponseDto>> login(@Valid @RequestBody UserSignInRequestDto dto) {
        ResponseDto<UserSignInResponseDto> response = authService.login(dto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 3) 이메일 전송
    @PostMapping("/send-email")
    public Mono<ResponseEntity<String>> sendEmail(@Valid @RequestBody EmailVerificationCodeRequestDto dto) {
        return mailService.sendSimpleMessage(dto.getEmail());
    }

    // 4) 이메일 인증
    @GetMapping("/verify")
    public Mono<ResponseEntity<String>> verifyEmail(@RequestParam String token) {
        return mailService.verifyEmail(token);
    }

    // 비밀번호 재설정
    @PostMapping("/reset-password")
    public Mono<ResponseEntity<String>> resetPassword(@Valid @RequestBody UserPasswordResetRequestDto dto) {
        return authService.resetPassword(dto);
    }
}
