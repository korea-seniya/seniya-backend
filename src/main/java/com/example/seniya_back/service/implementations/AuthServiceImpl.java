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
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.DuplicateFormatFlagsException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class  AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtProvider jwtProvider;

    @Override
    public ResponseDto<UserSignUpResponseDto> signup(UserSignUpRequestDto dto) {

        String username = dto.getUserName();
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

        // 패스워드 암호화
        String encodePassword = bCryptPasswordEncoder.encode(password);

        User user = User.builder()
                .userName(username)
                .password(encodePassword)
                .email(email)
                .name(name)
                .phone(phone)
                .build();

        userRepository.save(user);

        UserSignUpResponseDto data = UserSignUpResponseDto.builder()
                .user(user)
                .build();
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, data).getBody();
    }


    @Override
    public ResponseDto<UserSignInResponseDto> login(UserSignInRequestDto dto) {
        String userName = dto.getUserName();
        String password = dto.getPassword();

        UserSignInResponseDto data = null;
        User user = null;

        int exprTime = jwtProvider.getExpiration();

        user = userRepository.findByUserName(userName)
                .orElse(null);

        if (user == null) {
           throw new EntityNotFoundException(ResponseCode.USER_NOT_FOUND);
        }

        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            // .matches(평문 비밀번호, 암호화된 비밀번호)
            // : 평문 비밀번호(실제 비밀번호)와 암호화된 비밀번호를 비교하여 일치 여부 반환(boolean)
            throw new IllegalArgumentException(ResponseCode.FAIL);
        }

        String role = user.getRole().getRoleName();

        String token = jwtProvider.generateToken(userName, role);

        data = new UserSignInResponseDto(token, user, exprTime);
        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS,data).getBody();
    }


}