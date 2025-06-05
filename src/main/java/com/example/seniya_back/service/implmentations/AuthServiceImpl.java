//package com.example.seniya_back.service.implmentations;
//
//import com.example.seniya_back.common.constants.ResponseCode;
//import com.example.seniya_back.common.constants.ResponseMessage;
//import com.example.seniya_back.dto.ResponseDto;
//import com.example.seniya_back.dto.user.request.UserPasswordResetRequestDto;
//import com.example.seniya_back.dto.user.request.UserSignInRequestDto;
//import com.example.seniya_back.dto.user.request.UserSignUpRequestDto;
//import com.example.seniya_back.dto.user.response.UserSignInResponseDto;
//import com.example.seniya_back.dto.user.response.UserSignUpResponseDto;
//import com.example.seniya_back.entity.Role;
//import com.example.seniya_back.entity.User;
//import com.example.seniya_back.provider.JwtProvider;
//import com.example.seniya_back.repository.RoleRepository;
//import com.example.seniya_back.repository.UserRepository;
//import com.example.seniya_back.service.AuthService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Service
//@RequiredArgsConstructor
//public class AuthServiceImpl implements AuthService {
//    private final UserRepository userRepository;
//    private final RoleRepository roleRepository;
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//    private final JwtProvider jwtProvider;
//
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
//        String confirmPassword  = dto.getConfirmPassword();
//        String email = dto.getEmail();
//        String phone = dto.getPhone();
//
//        UserSignUpResponseDto data = null;
//        User user = null;
//
//        if (!password.equals(confirmPassword)) {
//            throw new IllegalArgumentException("입력하신 비밀번호와 확인 비밀번호가 일치 하지 않습니다.");
//        }
//
//        if (userRepository.existsByEmail(email)) {
//            throw new IllegalArgumentException(ResponseMessage.DUPLICATED_EMAIL);
//        }
//
//        if (userRepository.existByPhone(phone)) {
//            throw new IllegalArgumentException(ResponseMessage.DUPLICATED_EMAIL);
//        }
//
//        String encodePassword =  bCryptPasswordEncoder.encode(password);
//
//        Role userRole = roleRepository.findByRoleName("USER")
//                .orElseGet(() -> roleRepository.save(Role.builder().roleName("USER").build()));
//
//        Set<Role> roleSet = new HashSet<>();
//        roleSet.add(userRole);
//
//        user = User.builder()
//                .userName(userName)
//                .password(encodePassword)
//                .email(email)
//                .build();
//
//        userRepository.save(user);
//
//        data = new UserSignUpResponseDto(user);
//
//        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data).getBody();
//    }
//
//    @Override
//    public ResponseDto<UserSignInResponseDto> login(UserSignInRequestDto dto) {
//        String userName = dto.getUserName();
//        String password = dto.getPassword();
//
//        UserSignInResponseDto data = null;
//        User user = null;
//
//        int exprTime = jwtProvider.getExpiration();
//
//        user = userRepository.findByUserName(userName)
//                .orElse(null);
//
//        if (user == null) {
//            throw new IllegalArgumentException(ResponseMessage.USER_NOT_FOUND);
//        }
//
//        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//        }
//
//        String token = jwtProvider.generateToken(userName, role);
//
//        return null;
//    }
//
//    @Override
//    public Mono<ResponseEntity<String>> resetPassword(UserPasswordResetRequestDto dto) {
//        return null;
//    }
//
////    // 1) 회원 가입
////    @Override
////    public ResponseDto<UserSignUpResponseDto> signup(UserSignUpRequestDto dto) {
////        String email = dto.getEmail();
////        String password = dto.getPassword();
////        String confirmPassword = dto.getConfirmPassword();
////
////        UserSignUpResponseDto data = null;
////        User user = null;
////
////        // 패스워드 일치 여부 확인
////        if (!password.equals(confirmPassword)) {
////            // 일치하지 않은 경우
////            return ResponseDto.setFailed(ResponseMessage.NOT_MATCH_PASSWORD);
////        }
////
////        // 중복되는 이메일 검증
////        if (userRepository.existsByEmail(email)) {
////            // 중복 되는 경우 (사용할 수 X)
////            return ResponseDto.setFailed(ResponseMessage.EXIST_DATA);
////        }
////
////        // 패스워드 암호화
////        String encodePassword = bCryptPasswordEncoder.encode(password);
////
////        // 권한 정보 확인
////        Role userRole = roleRepository.findByRoleName("USER")
////                .orElseGet(() -> roleRepository.save(Role.builder().roleName("USER").build()));
////
////        Set<Role> roleSet = new HashSet<>();
////        roleSet.add(userRole);
////
////        user = User.builder()
////                .email(email)
////                .password(encodePassword)
////                .createdAt(LocalDateTime.now())
////                .roles(roleSet)
////                .build();
////
////        userRepository.save(user);
////
////        data = new UserSignUpResponseDto(user);
////        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
////    }
////
////    // 2) 로그인
////    @Override
////    public ResponseDto<UserSignInResponseDto> login(UserSignInRequestDto dto) {
////        String email = dto.getEmail();
////        String password = dto.getPassword();
////
////        UserSignInResponseDto data = null;
////        User user = null;
////
////        int exprTime = jwtProvider.getExpiration();
////
////        user = userRepository.findByEmail(email)
////                .orElse(null);
////
////        if (user == null) {
////            return ResponseDto.setFailed(ResponseMessage.NOT_EXISTS_USER);
////        }
////
////        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
////            // .matches(평문 비밀번호, 암호화된 비밀번호)
////            // : 평문 비밀번호(실제 비밀번호)와 암호화된 비밀번호를 비교하여 일치 여부 반환(boolean)
////            return ResponseDto.setFailed(ResponseMessage.NOT_MATCH_PASSWORD);
////        }
////
////        // 사용자 정보의 권한 정보를 호출
////        Set<String> roles = user.getRoles().stream()
////                .map(Role::getRoleName)
//////              .map(role -> role.getRoleName())
////                .collect(Collectors.toSet());
////
////        String token = jwtProvider.generateJwtToken(email, roles); // username에 email 저장
////
////        data = new UserSignInResponseDto(token, user, exprTime);
////        return ResponseDto.setSuccess(ResponseMessage.SUCCESS, data);
////    }
////
////    @Override
////    public Mono<ResponseEntity<String>> resetPassword(PasswordResetRequestDto dto) {
////        return Mono.fromCallable(() -> {
////            User user = userRepository.findByEmail(dto.getEmail())
////                    .orElseThrow(() -> new IllegalArgumentException("가입된 이메일이 아닙니다."));
////
//////                if (!user.isEmailVerified()) {
//////                    return ResponseEntity.badRequest().body("이메일 인증이 필요합니다.");
//////                }
////
////            // 비밀번호, 비밀번호 확인 유효성 검사 필수! (일치 여부, 형식 여부)
////
////            user.setPassword(bCryptPasswordEncoder.encode(dto.getNewPassword()));
////            userRepository.save(user);
////
////            return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
////        }).onErrorResume(e -> Mono.just(
////                ResponseEntity.badRequest().body("비밀번호 재설정 실패: " + e.getMessage())
////        )).subscribeOn(Schedulers.boundedElastic());
////    }
//}