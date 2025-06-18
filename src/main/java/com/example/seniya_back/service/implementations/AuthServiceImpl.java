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
public class  AuthServiceImpl implements AuthService {
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

        // 패스워드 일치 여부 확인
        if (!password.equals(confirmPassword)) {
            // 일치하지 않은 경우
            throw new IllegalArgumentException(ResponseCode.FAIL);
        }

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new DuplicateFormatFlagsException(ResponseCode.FAIL);
        }

        // 권한 정보 확인
        Role userRole = roleRepository.findByRoleName("USER")
                .orElseGet(() -> roleRepository.save(Role.builder().roleName("USER").build()));

        System.out.println(userRole.getRoleName());
        // 패스워드 암호화
        String encodePassword = bCryptPasswordEncoder.encode(password);

        User user = User.builder()
                .username(username)
                .password(encodePassword)
                .email(email)
                .name(name)
                .phone(phone)
                .role(userRole)
                .build();

        userRepository.save(user);

        UserSignUpResponseDto data = new UserSignUpResponseDto();
        return ResponseDto.success(ResponseCode.SUCCESS, "회원 가입이 완료되었습니다.",data).getBody();

    }


    @Override
    public ResponseDto<UserSignInResponseDto> login(UserSignInRequestDto dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();

        UserSignInResponseDto data = null;

        User user = userRepository.findByUsername(username)
                .orElse(null);

        int exprTime = jwtProvider.getExpiration();

        if (user == null) {
           throw new EntityNotFoundException(ResponseCode.USER_NOT_FOUND);
        }

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            // .matches(평문 비밀번호, 암호화된 비밀번호)
            // : 평문 비밀번호(실제 비밀번호)와 암호화된 비밀번호를 비교하여 일치 여부 반환(boolean)
            throw new IllegalArgumentException(ResponseCode.FAIL);
        }

        UserResponseDto responseDto = new UserResponseDto(
                user.getUserId(), user.getRole().getRoleName(), user.getName()
        );

        String token = jwtProvider.generateToken(user.getUsername(), user.getRole().getRoleName(), user.getId());


        data = new UserSignInResponseDto(token, responseDto, exprTime);
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS,data).getBody();
    }

    @Override
    public ResponseDto<?> logout(String username) {
        return ResponseDto.success(ResponseCode.SUCCESS, "로그아웃 처리 완료").getBody();
    }



    @Override
    public Mono<ResponseEntity<String>> resetPassword(UserPasswordResetRequestDto dto) {
        return Mono.fromCallable(() -> {
            User user = (User) userRepository.findByEmail(dto.getEmail())
                    .orElseThrow(() -> new IllegalArgumentException("가입된 이메일이 아닙니다."));

            if (!user.isEmailVerified()) {
                return ResponseEntity.badRequest().body("이메일 인증이 필요합니다.");
            }

            // 비밀번호, 비밀번호 확인 유효성 검사 필수! (일치 여부, 형식 여부)

            user.setPassword(bCryptPasswordEncoder.encode(dto.getNewPassword()));
            userRepository.save(user);

            return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
        }).onErrorResume(e -> Mono.just(
                ResponseEntity.badRequest().body("비밀번호 재설정 실패: " + e.getMessage())
        )).subscribeOn(Schedulers.boundedElastic());
        }
    }
