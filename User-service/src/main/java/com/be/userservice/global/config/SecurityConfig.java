package com.be.userservice.global.config;

import com.be.userservice.global.security.JwtAuthenticationFilter;
import io.swagger.v3.oas.models.PathItem.HttpMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final CorsConfig corsConfig;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        // CSRF 보호 기능 비활성화 (REST API에서는 필요없음)
        .csrf(AbstractHttpConfigurer::disable)
        // CORS 설정 활성화(보통은 CORS 설정 활성화 하지 않음. 서버에서 NginX로 CORS 검증)
        .cors(cors -> cors.configurationSource(corsConfig.corsConfigurationSource()))
        // HTTP Basic 인증 기본 설정
        .httpBasic(Customizer.withDefaults())
        // 세션을 생성하지 않음 (JWT 사용으로 인한 Stateless 설정)
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // HTTP 요청에 대한 권한 설정
        .authorizeHttpRequests(
            request ->
                request
                    // Swagger 경로 인증 필요 x
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                    // 로그인 허용
                    .requestMatchers("/api/auths/**").permitAll()
                    // 회원가입 허용
                    .requestMatchers("/api/users/**").permitAll()
                    // 인증 없이 허용할 경로
                    .requestMatchers("/api/v1/**").permitAll()
                    // 그 외 모든 요청은 모두 인증 필요
                    .anyRequest()
                    .authenticated())
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  /**
   * 비밀번호 인코더 Bean 등록
   **/
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * 인증 관리자 Bean 등록
   **/
  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}