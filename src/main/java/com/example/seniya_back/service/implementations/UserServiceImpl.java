package com.example.seniya_back.service.implementations;

import com.example.seniya_back.common.constants.ResponseCode;
import com.example.seniya_back.common.constants.ResponseMessage;
import com.example.seniya_back.dto.ResponseDto;
import com.example.seniya_back.dto.user.request.MyInfoUpdateRequestDto;
import com.example.seniya_back.dto.user.request.UserSignInRequestDto;
import com.example.seniya_back.dto.user.response.GetMyInfoResponseDto;
import com.example.seniya_back.entity.User;
import com.example.seniya_back.repository.UserRepository;
import com.example.seniya_back.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseDto<GetMyInfoResponseDto> getUserInfo(String username) {
        GetMyInfoResponseDto dto = null;

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(ResponseMessage.USER_NOT_FOUND));

        if (user == null) {
            throw new IllegalArgumentException(ResponseMessage.USER_NOT_FOUND);
        }

        dto = GetMyInfoResponseDto.builder()
                .id(user.getUserId())
                .username(user.getUsername())
                .phone(user.getPhone())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt().toLocalDate())
                .updatedAt(user.getUpdatedAt().toLocalDate())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, dto).getBody();
    }

    @Override
    @Transactional
    public ResponseDto<GetMyInfoResponseDto> updateUserInfo(String username, MyInfoUpdateRequestDto dto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.USER_NOT_FOUND));

        if (!user.getUsername().equals(dto.getUsername())) {
            userRepository.findByUsername(dto.getUsername()).ifPresent(existingUser -> {
                throw new IllegalArgumentException("이미 사용 중인 사용자명입니다.");
            });
            user.setUsername(dto.getUsername());
        }

        user.setPhone(dto.getPhone());
        user.setEmail(dto.getEmail());

        GetMyInfoResponseDto responseDto = GetMyInfoResponseDto.builder()
                .id(user.getUserId())
                .username(user.getUsername())
                .phone(user.getPhone())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt().toLocalDate())
                .updatedAt(user.getUpdatedAt().toLocalDate())
                .build();

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, responseDto).getBody();
    }

    @Override
    @Transactional
    public ResponseDto<?> deleteUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException(ResponseMessage.USER_NOT_FOUND));

        userRepository.delete(user);

        return ResponseDto.success(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, null).getBody();
    }

    @Override
    @Transactional
    public void signIn(UserSignInRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 로그인 성공 시 필요한 작업 (예: JWT 토큰 발급 등)
        System.out.println("로그인 성공!");
    }

}
