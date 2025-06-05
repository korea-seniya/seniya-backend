package com.example.seniya_back.service.implementations;

import com.example.seniya_back.dto.user.request.UserSignInRequestDto;
import com.example.seniya_back.entity.User;
import com.example.seniya_back.repository.UserRepository;
import com.example.seniya_back.service.UserService;
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
    @Transactional
    public void signIn(UserSignInRequestDto requestDto) {
        User user = userRepository.findByUserName(requestDto.getUserName())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 로그인 성공 시 필요한 작업 (예: JWT 토큰 발급 등)
        System.out.println("로그인 성공!");
    }
}
