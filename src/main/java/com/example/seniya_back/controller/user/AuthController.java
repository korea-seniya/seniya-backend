package com.example.seniya_back.controller.user;

import com.example.seniya_back.common.constants.ApiMappingPattern;
import com.example.seniya_back.common.constants.ResponseCode;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.user.request.EmailSendRequestDto;
import com.example.seniya_back.dto.user.request.UserPasswordResetRequestDto;
import com.example.seniya_back.dto.user.request.UserSignInRequestDto;
import com.example.seniya_back.dto.user.request.UserSignUpRequestDto;
import com.example.seniya_back.dto.user.response.UserSignInResponseDto;
import com.example.seniya_back.dto.user.response.UserSignUpResponseDto;
import com.example.seniya_back.provider.JwtProvider;
import com.example.seniya_back.service.AuthService;
import com.example.seniya_back.service.MailService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.AbstractMap;

@RestController
@RequestMapping(ApiMappingPattern.AUTH_API)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MailService mailService;
    private final JwtProvider jwtProvider;
    private static final String SIGN_UP = "/signup";
    private static final String SIGN_IN = "/signin";
    private static final String LOG_OUT = "/logout";
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<UserSignUpResponseDto>> signup(@Valid @RequestBody UserSignUpRequestDto dto) {
        ResponseDto<UserSignUpResponseDto> response = authService.signup(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<ResponseDto<UserSignInResponseDto>> login(@Valid @RequestBody UserSignInRequestDto dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseDto<?>> logout(@AuthenticationPrincipal String username) {
        return ResponseEntity.ok(authService.logout(username));
    }

    // 인증 메일 전송
    @PostMapping("/send-email")
    public Mono<ResponseEntity<String>> sendEmail(@Valid @RequestBody EmailSendRequestDto dto) {
        return mailService.sendSimpleMessage(dto.getEmail());
    }

    @GetMapping("/verification-codes/email")
    public Mono<ResponseEntity<Void>> verifyEmail(@RequestParam String token) {
        return Mono.fromCallable(() -> jwtProvider.getClaims(token))
                .map(claims -> {
                    String email = claims.get("email", String.class);
                    return new AbstractMap.SimpleEntry<>(email, token); // 이메일 + 토큰 같이 리턴
                })
                .flatMap(entry -> mailService.completeEmailVerification(entry.getKey())
                        .thenReturn(ResponseEntity
                                .status(HttpStatus.FOUND)
                                .header(HttpHeaders.LOCATION,
                                        "http://localhost:5176/users/me/password-reset?token=" +
                                                entry.getValue() + "&email=" + entry.getKey())
                                .<Void>build()))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).<Void>build()));
    }



    // 비밀번호 재설정
    @PutMapping("/reset-password")
    public Mono<ResponseEntity<String>> resetPassword(@Valid @RequestBody UserPasswordResetRequestDto dto) {
        return authService.verifyResetPasswordToken(dto.getToken())
                .flatMap(email -> authService.resetPassword(email, dto.getNewPassword()))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().body(e.getMessage())));
    }


    // 7) 아이디 중복 확인
    @GetMapping("/check-username")
    public ResponseEntity<ResponseDto<Boolean>> checkUsername(@RequestParam String username) {
        boolean available = !authService.existsByUsername(username);
        String message = available ? "사용 가능한 아이디 입니다." : "이미 존재하는 아이디입니다.";
        return ResponseEntity.ok(ResponseDto.success(ResponseCode.SUCCESS, message, available).getBody());
    }

    // 8) 이메일 중복 확인
    @GetMapping("/check-email")
    public ResponseEntity<ResponseDto<Boolean>> checkEmail(@RequestParam String email) {
        boolean available = !authService.existsByEmail(email);
        String message = available ? "사용 가능한 이메일 주소 입니다." : "이미 존재하는 이메일 주소입니다.";
        return ResponseEntity.ok(ResponseDto.success(ResponseCode.SUCCESS, message, available).getBody());
    }
    @GetMapping("/email-verified")
    public ResponseEntity<Boolean> isEmailVerified(@RequestParam String email) {
        boolean verified = authService.isEmailVerified(email);
        return ResponseEntity.ok(verified);
    }



}

