package com.example.seniya_back.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebFileConfig implements WebMvcConfigurer {
    // 브라우저에서 httpL//localhost:포트/files/파일명으로 접근할 수 있도록 정적 리소스 경로를 매핑
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**")
                .addResourceLocations("file:C:/upload/file/");
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:C:///upload/file/");
    }

}