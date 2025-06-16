package com.example.seniya_back.handler;

import com.example.seniya_back.entity.User;
import com.example.seniya_back.provider.JwtProvider;
import com.example.seniya_back.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.net.URI;

@Component
public class EmailHandler {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    public EmailHandler(JwtProvider jwtProvider, UserRepository userRepository) {
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
    }

    public Mono<ServerResponse> verifyEmail(ServerRequest request) {
        String token = request.queryParam("token").orElse(null);

        if (token == null) {
            return ServerResponse.badRequest().bodyValue("토큰이 누락되었습니다.");
        }

        return Mono.fromCallable(() -> {
                    String email = jwtProvider.getUsernameFromJwt(token);

                    // 동기 호출로 User 조회
                    User user = userRepository.findByEmail(email)
                            .orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));

                    user.verifyEmail();

                    // 저장
                    userRepository.save(user);

                    return URI.create("https://localhost:5173/auth/password-reset?token=" + token);
                })
                .flatMap(uri -> ServerResponse.temporaryRedirect(uri).build())
                .onErrorResume(e -> ServerResponse.badRequest().bodyValue("이메일 인증 실패: " + e.getMessage()))
                .subscribeOn(Schedulers.boundedElastic());
    }
}