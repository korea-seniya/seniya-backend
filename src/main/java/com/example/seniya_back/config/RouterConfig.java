package com.example.seniya_back.config;

import com.example.seniya_back.handler.EmailHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(EmailHandler handler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/auth/verification-codes/email"), handler::verifyEmail);
        // 정확한 하위 경로 추가
        // +) 쿼리 파라미터는 포함 X
    }
}