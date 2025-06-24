package com.example.seniya_back.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebFileConfig implements WebMvcConfigurer {
    @Value("${file.upload-dir}")
    private String uploadDir;
    // 브라우저에서 httpL//localhost:포트/files/파일명으로 접근할 수 있도록 정적 리소스 경로를 매핑
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /files/** URL 패턴 요청이 오면
        registry.addResourceHandler("/files/**")
                // uploadDir 변수에 담긴 경로에서 파일을 찾도록 설정합니다.
                // 이렇게 하면 Windows, Mac, Linux 어디서든 동일하게 동작합니다.
                .addResourceLocations("file:" + uploadDir + "/");
        // 아래 /uploads/** 핸들러는 중복되거나 불필요해 보이므로
        // 팀원과 상의 후 삭제하거나, 필요하다면 유지합니다.
        // registry.addResourceHandler("/uploads/**")
        //         .addResourceLocations("file:" + uploadDir + "/");
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:C:///upload/file/");
    }

}