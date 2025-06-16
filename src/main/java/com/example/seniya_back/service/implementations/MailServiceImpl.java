package com.example.seniya_back.service.implementations;

import com.example.seniya_back.entity.User;
import com.example.seniya_back.provider.JwtProvider;
import com.example.seniya_back.repository.UserRepository;
import com.example.seniya_back.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final Map<String, String> verificationTokens = new ConcurrentHashMap<>();
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    public Mono<ResponseEntity<String>> sendSimpleMessage(String email) {
        return Mono.fromSupplier(() -> {
            String token = jwtProvider.generateToken(email, "ROLE_USER");
            verificationTokens.put(token, email);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("이메일 인증 요청");
            message.setText("인증을 위해 아래 링크를 클릭하세요:\n" +
                    "http://localhost:8080/auth/verification-codes/email?token=" + token);
            mailSender.send(message);

            return ResponseEntity.ok("인증 메일 전송 완료");
        });
    }

    @Override
    public Mono<ResponseEntity<String>> verifyEmail(String token) {
        return Mono.fromCallable(() -> {
            String email = verificationTokens.remove(token);
            if (email != null) {
                // 이메일로 User 조회
                User user = userRepository.findByEmail(email)
                        .orElseThrow(() -> new IllegalArgumentException("해당 이메일의 사용자가 없습니다."));

                // 이메일 인증 상태 업데이트
                user.verifyEmail();  // emailVerified = true 설정 메서드
                userRepository.save(user);  // DB에 저장

                return ResponseEntity.ok("이메일 인증 성공: " + email);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("유효하지 않은 또는 만료된 토큰입니다.");
            }
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
