//package com.example.seniya_back.service.implementations;
//
//import com.example.seniya_back.provider.JwtProvider;
//import com.example.seniya_back.service.MailService;
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//import reactor.core.scheduler.Schedulers;
//
//@Service
//@RequiredArgsConstructor
//public class MailServiceImpl implements MailService {
//     private final JavaMailSender javaMailSender;
//     private final JwtProvider jwtProvider;
//
//    @Value("${spring.mail.username}")
//    private String senderEmail;
//
//    private MimeMessage createMail(String email, String token) throws MessagingException {
//        MimeMessage message = javaMailSender.createMimeMessage();
//        message.setFrom(senderEmail);
//        message.setRecipients(MimeMessage.RecipientType.TO, email);
//
//        message.setSubject("== 이메일 인증 링크 발송 ==");
//
//        String body = """
//                <h3>이메일 인증링크입니다.</h3>
//                <a href="http://localhost:8080/api/v1/auth/verify?token=%s">여기를 클릭하여 인증을 완료해주세요.</a>
//                """.formatted(token);
//
//        message.setText(body, "UTF-8", "html");
//        return message;
//    }
//
//
//    public Mono<ResponseEntity<String>> sendSimpleMessage(String email) {
//
//
//        return Mono.fromCallable(() -> {
//            String token = jwtProvider.generateToken(email);
//            MimeMessage message = createMail(email, token);
//            javaMailSender.send(message);
//            return ResponseEntity.ok("인증 이메일이 전송되었습니다.");
//
//
//        }).onErrorResume(e -> Mono.just(
//                        ResponseEntity.badRequest().body("이메일 전송 실패: " + e.getMessage()))
//
//        ).subscribeOn(Schedulers.boundedElastic());
//    }
//
//    @Override
//    public Mono<ResponseEntity<String>> verifyEmail(String token) {
//        return Mono.fromCallable(() -> {
//            String email = jwtProvider.getUsernameFromJwt(token);
//            return ResponseEntity.ok("이메일 인증이 완료되었습니다. 사용자: " + email);
//        }).onErrorResume(e -> Mono.just(
//                ResponseEntity.badRequest().body("이메일 인증 실패: " + e.getMessage()))
//        ).subscribeOn(Schedulers.boundedElastic());
//    }
//}
