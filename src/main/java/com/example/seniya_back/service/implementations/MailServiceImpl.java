package com.example.seniya_back.service.implementations;

import com.example.seniya_back.provider.JwtProvider;
import com.example.seniya_back.repository.UserRepository;
import com.example.seniya_back.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;


@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Override
    public Mono<ResponseEntity<String>> sendSimpleMessage(String email) {
        return Mono.fromSupplier(() -> {
            Long userId = userRepository.findByEmail(email)
                    .map(user -> user.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

            String token = jwtProvider.generateToken(email, "ROLE_USER", userId);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("이메일 인증 요청");
            message.setText("인증을 위해 아래 링크를 클릭하세요:\n" +
                    "http://localhost:8080/api/v1/auth/verification-codes/email?token=" + token);

            mailSender.send(message);

            return ResponseEntity.ok("인증 메일 전송 완료");
        });
    }

    @Override
    public Mono<Void> completeEmailVerification(String email) {
        return Mono.fromRunnable(() -> {
            userRepository.findByEmail(email).ifPresent(user -> {
                user.setEmailVerified(true);
                userRepository.save(user);
            });
            System.out.println("이메일 인증 처리 완료: " + email);
        }).subscribeOn(Schedulers.boundedElastic()).then();
    }
}
