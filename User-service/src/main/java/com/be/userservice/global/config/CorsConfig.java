package com.be.userservice.global.config;

import java.util.Arrays;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig { // 서로 다른 도메인 또는 서버 간에 자원을 요청하고 받을 때 생기는 보안제약을 해결
  @Value("${cors.allowed-origins}") // 3000포트 : 리엑트 기본포트
  private String[] allowedOrigins;

  @Bean
  public UrlBasedCorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    // 환경 변수(application.properties)에 정의된 출처만 허용
    configuration.setAllowedOrigins(Arrays.asList(allowedOrigins));
    // 리스트에 작성한 HTTP 메소드 요청만 허용
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH"));
    // 리스트에 작성한 헤더들이 포함된 요청만 허용
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
    // 쿠키나 인증 정보를 포함하는 요청 허용
    configuration.setAllowCredentials(true);
    // 클라이언트가 Authorization 헤더를 읽을 수 있도록 허용(JWT 를 사용할 경우)
    configuration.setExposedHeaders(List.of("Authorization"));
    // 모든 경로에 대해 위의 CORS 설정을 허용
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;

  }

}
