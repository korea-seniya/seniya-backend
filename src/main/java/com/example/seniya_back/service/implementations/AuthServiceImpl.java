package com.example.seniya_back.service.implementations;

import com.example.seniya_back.common.constants.ResponseCode;
import com.example.seniya_back.common.constants.ResponseMessage;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.user.request.UserPasswordResetRequestDto;
import com.example.seniya_back.dto.user.request.UserSignInRequestDto;
import com.example.seniya_back.dto.user.request.UserSignUpRequestDto;
import com.example.seniya_back.dto.user.response.UserSignInResponseDto;
import com.example.seniya_back.dto.user.response.UserSignUpResponseDto;
import com.example.seniya_back.entity.Role;
import com.example.seniya_back.entity.User;
import com.example.seniya_back.provider.JwtProvider;
import com.example.seniya_back.repository.RoleRepository;
import com.example.seniya_back.repository.UserRepository;
import com.example.seniya_back.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public ResponseDto<UserSignUpResponseDto> signup(UserSignUpRequestDto dto) {
//        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
//            return ResponseDto.fail("", "이미 사용 중인 이메일입니다.");
//        }
//
//        Role userRole = roleRepository.findByRoleName("USER")
//                .orElseGet(() -> roleRepository.save(Role.builder().roleName("USER").build()));

        User user = User.builder()
                .userName(dto.getUserName())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .name(dto.getName())
                .phone(dto.getPhone())
                .build();

        userRepository.save(user);

        UserSignUpResponseDto data = UserSignUpResponseDto.builder()
                .user(user)
                .build();
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data).getBody();
    }


    @Override
    public ResponseDto<UserSignInResponseDto> login(UserSignInRequestDto dto) {
        return null;
    }

//    @Value("${jwt.expiration}")
//    private String jwtExpirationMs;
//
//    @Value("${jwt.secret}")
//    private String jwtSecret;
//
//    // 회원가입
//    @Override
//    public ResponseDto<UserSignUpResponseDto> signup(UserSignUpRequestDto dto) {
//        String userName = dto.getUserName();
//        String password = dto.getPassword();
//        String name = dto.getName();
//        String confirmPassword  = dto.getConfirmPassword();
//        String email = dto.getEmail();
//        String phone = dto.getPhone();
//
//        if (!password.equals(confirmPassword)) {
//            throw new IllegalArgumentException("입력하신 비밀번호와 확인 비밀번호가 일치 하지 않습니다.");
//        }
//
////        if (userRepository.existsByEmail(email)) {
////            throw new IllegalArgumentException(ResponseMessage.DUPLICATED_EMAIL);
////        }
////
////        if (userRepository.existByPhone(phone)) {
////            throw new IllegalArgumentException(ResponseMessage.DUPLICATED_EMAIL);
////        }
//
//        String encodePassword =  bCryptPasswordEncoder.encode(password);
//
//        Role userRole = roleRepository.findByRoleName("USER")
//                .orElseGet(() -> roleRepository.save(Role.builder().roleName("USER").build()));
//
//        Set<Role> roleSet = new HashSet<>();
//        roleSet.add(userRole);
//
//        User user = User.builder()
//                .userName(userName)
//                .password(encodePassword)
//                .name(name)
//                .email(email)
//                .phone(phone)
//                .roles(roleSet)
//                .build();
//
//        userRepository.save(user);
//
//        UserSignUpResponseDto data = new UserSignUpResponseDto(user);
//
//        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data).getBody();
//    }
//
//    @Override
//    public ResponseDto<UserSignInResponseDto> login(UserSignInRequestDto dto) {
//        String userName = dto.getUserName();
//        String password = dto.getPassword();
//
//        User user = userRepository.findByUserName(userName)
//                .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.USER_NOT_FOUND));
//
//        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//        }
//
//        Set<String> roles = user.getRoles().stream()
//                .map(Role::getRoleName)
//                .collect(Collectors.toSet());
//
//        String roleString = String.join(",", roles);
//        String token = jwtProvider.generateToken(userName, roleString);
//
//        int exprTime = jwtProvider.getExpiration();
//
//        UserSignInResponseDto responseData = new UserSignInResponseDto(token, user, exprTime);
//
//        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseData).getBody();
//    }
//
//    @Override
//    public Mono<ResponseEntity<String>> resetPassword(UserPasswordResetRequestDto dto) {
//        return Mono.fromCallable(() -> {
//            User user = userRepository.findByUserName(dto.getUserName())
//                    .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.USER_NOT_FOUND));
//
//            if (!user.getEmail().equals(dto.getEmail()) || !user.getPhone().equals(dto.getPhone())) {
//                throw new IllegalArgumentException("입력한 이메일 또는 전화번호가 일치하지 않습니다.");
//            }
//
//            if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
//                throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
//            }
//
//            user.setPassword(bCryptPasswordEncoder.encode(dto.getNewPassword()));
//            userRepository.save(user);
//
//            return ResponseEntity.ok(ResponseMessage.SUCCESS);
//        }).onErrorResume(e -> Mono.just(
//                ResponseEntity.badRequest().body("비밀번호 재설정 실패" + e.getMessage())
//        )).subscribeOn(Schedulers.boundedElastic());
//    }

}