package com.example.seniya_back.service.implementations;

import com.example.seniya_back.common.constants.ResponseCode;
import com.example.seniya_back.common.constants.ResponseMessage;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.user.request.UserPasswordResetRequestDto;
import com.example.seniya_back.dto.user.request.UserSignInRequestDto;
import com.example.seniya_back.dto.user.request.UserSignUpRequestDto;
import com.example.seniya_back.dto.user.response.UserResponseDto;
import com.example.seniya_back.dto.user.response.UserSignInResponseDto;
import com.example.seniya_back.dto.user.response.UserSignUpResponseDto;
import com.example.seniya_back.entity.Role;
import com.example.seniya_back.entity.User;
import com.example.seniya_back.provider.JwtProvider;
import com.example.seniya_back.repository.RoleRepository;
import com.example.seniya_back.repository.UserRepository;
import com.example.seniya_back.service.AuthService;
import io.jsonwebtoken.Claims;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.DuplicateFormatFlagsException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public ResponseDto<UserSignUpResponseDto> signup(UserSignUpRequestDto dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();
        String confirmPassword = dto.getConfirmPassword();
        String name = dto.getName();
        String email = dto.getEmail();
        String phone = dto.getPhone();

        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException(ResponseCode.FAIL);
        }

        if (userRepository.findByEmail(email).isPresent()) {
            throw new DuplicateFormatFlagsException(ResponseCode.FAIL);
        }

        Role userRole = roleRepository.findByRoleName("USER")
                .orElseGet(() -> roleRepository.save(Role.builder().roleName("USER").build()));

        String encodePassword = bCryptPasswordEncoder.encode(password);

        User user = User.builder()
                .username(username)
                .password(encodePassword)
                .email(email)
                .name(name)
                .phone(phone)
                .role(userRole)
                .emailVerified(false) // 회원가입 시 기본값 false로 설정
                .build();

        userRepository.save(user);

        UserSignUpResponseDto data = new UserSignUpResponseDto();
        return ResponseDto.success(ResponseCode.SUCCESS, "회원 가입이 완료되었습니다.", data).getBody();
    }

    @Override
    public ResponseDto<UserSignInResponseDto> login(UserSignInRequestDto dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseCode.USER_NOT_FOUND));

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException(ResponseCode.FAIL);
        }

        UserResponseDto responseDto = new UserResponseDto(
                user.getUserId(), user.getRole().getRoleName(), user.getName()
        );

        String token = jwtProvider.generateToken(user.getUsername(), user.getRole().getRoleName());
        int exprTime = jwtProvider.getExpiration();
        int roleId = user.getRole().getId();
        UserSignInResponseDto data = new UserSignInResponseDto(token, responseDto, exprTime, roleId);
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data).getBody();
    }

    @Override
    public ResponseDto<?> logout(String username) {
        return ResponseDto.success(ResponseCode.SUCCESS, "로그아웃 처리 완료").getBody();
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    // 이메일 인증 완료 처리 - email_verified 컬럼 true로 변경
    @Override
    public Mono<Void> completeEmailVerification(String email) {
        return Mono.fromRunnable(() -> {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("가입된 이메일이 아닙니다."));
            if (!user.isEmailVerified()) {
                user.setEmailVerified(true);
                userRepository.save(user);
            }
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }

    @Override
    public Mono<String> verifyResetPasswordToken(String token) {
        try {
            Claims claims = jwtProvider.getClaims(token);
            String email = claims.get("email", String.class);

            return Mono.just(email)
                    .flatMap(e -> Mono.fromCallable(() -> {
                        User user = userRepository.findByEmail(e)
                                .orElseThrow(() -> new IllegalArgumentException("가입된 이메일이 아닙니다."));
                        if (!user.isEmailVerified()) {
                            throw new IllegalArgumentException("이메일 인증이 필요합니다.");
                        }
                        return e;
                    }).subscribeOn(Schedulers.boundedElastic()));
        } catch (Exception e) {
            return Mono.error(new IllegalArgumentException("유효하지 않은 토큰입니다."));
        }
    }


    @Override
    public Mono<ResponseEntity<String>> resetPassword(String email, String newPassword) {
        return Mono.fromCallable(() -> {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new IllegalArgumentException("가입된 이메일이 아닙니다."));
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            userRepository.save(user);
            return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
        }).subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<ResponseEntity<String>> resetPassword(UserPasswordResetRequestDto dto) {
        return verifyResetPasswordToken(dto.getToken())
                .flatMap(email -> resetPassword(email, dto.getNewPassword()))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.badRequest().body("비밀번호 재설정 실패: " + e.getMessage())
                ));
    }

    @Override
    public boolean isUsernameAvailable(String username) {
        return !userRepository.existsByUsername(username);
    }

    @Override
    public boolean isEmailVerified(String email) {
        return userRepository.findByEmail(email)
                .map(User::isEmailVerified)
                .orElse(false);
    }
}
