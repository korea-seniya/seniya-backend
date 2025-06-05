package com.example.seniya_back.service;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface MailService {
    Mono<ResponseEntity<String>> sendSimpleMessage(String email);

    Mono<ResponseEntity<String>> verifyEmail(String token);
}
